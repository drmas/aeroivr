/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */

#include <ptlib.h>
#include "OpenH323JNIWavFileChannel.h"

OpenH323JNIWavFileChannel::OpenH323JNIWavFileChannel(
	H323Connection & h323Connection) : connection(h323Connection), 
	isOpened(true), closeChannel(false)
{
}

BOOL OpenH323JNIWavFileChannel::OpenWavFile(const PString & wavFileName)
{
	if (wavFile.IsOpen())
	{
		wavFile.Close();
	}

	wavFile.Open(wavFileName, PFile::ReadOnly, PFile::Temporary);

	if (!wavFile.IsOpen()) 
    {
        PError << "Failed to open WAV file " << wavFileName << endl;
		return false;
    } 
	else
	{
		if (wavFile.GetFormat() != PWAVFile::fmt_PCM 
			|| wavFile.GetChannels() != 1
			|| wavFile.GetSampleRate() != 8000
			|| wavFile.GetSampleSize() != 16)
		{
			PError << "WAV file " << wavFileName << " has wrong format." << endl;
			wavFile.Close();
			return false;
		}
	}
	return true;
}

void OpenH323JNIWavFileChannel::AddFileNameToPlay(const PString & fileName)
{
	wavFileNames.EnqueueString(new PString(fileName));
}

void OpenH323JNIWavFileChannel::CloseChannelAfterLastWavFile()
{
	closeChannel = true;
}

BOOL OpenH323JNIWavFileChannel::Close()
{
	wavFileNames.Clear();
	if (wavFile.IsOpen())
	{
		return wavFile.Close();
	}
	else
	{
		return true;
	}
}

BOOL OpenH323JNIWavFileChannel::IsOpen() const
{
	return isOpened;
}

void OpenH323JNIWavFileChannel::OpenNextWavFileFromQueue()
{
	BOOL successfullOpened = false;
	PString * wavFileName = NULL;
	do
	{
		wavFileName = wavFileNames.DequeueString();
		if (NULL != wavFileName)
		{
			__try
			{
				successfullOpened = OpenWavFile(*wavFileName);
			}
			__finally
			{
				delete wavFileName;
			}
		}
	}
	while (!successfullOpened && (NULL != wavFileName));
}

BOOL OpenH323JNIWavFileChannel::Read(void * buffer, PINDEX length)
{
	memset(buffer, 0, length);
	lastReadCount = length;

    if (!connection.IsEstablished()) 
    {
		PTRACE(TRACE_INFORMATION, "WAV FILE: Connection not established yet");
		readDataDelay.Delay(lastReadCount/2/8); 
        return true; 
    } 
    
	if (wavFile.IsOpen())
	{
		if (!wavFile.Read(buffer, length)) 
		{
			PTRACE(TRACE_INFORMATION, "WAV FILE: File fully readed, getting new one");
			OpenNextWavFileFromQueue();
		}
		else
		{
			// PTRACE(TRACE_INFORMATION,  "WAV FILE: Read " << wavFile.GetLastReadCount() << " of bytes from WAV file");
			if (wavFile.GetLastReadCount() < length)
			{
				wavFile.Close();
			}
		}
	}
	else
	{
		//PTRACE(TRACE_INFORMATION, "WAV FILE: File is closed. Going to new one");
		OpenNextWavFileFromQueue();
	}
            
    readDataDelay.Delay(lastReadCount/2/8); 

	if (closeChannel && wavFileNames.IsEmpty() && !wavFile.IsOpen())
	{
		PTRACE(TRACE_INFORMATION, "WAV FILE: Closing connection");
		isOpened = false;
		connection.ClearCall();
		return false;
	}
	else
	{
		return true; 
	}
}

BOOL OpenH323JNIWavFileChannel::Write(const void * buffer, PINDEX length)
{
    lastWriteCount = length; 
    writeDataDelay.Delay(length/2/8); 
    return true;
}