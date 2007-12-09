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

#include "OpenH323JNIWavFileChannel.h"

OpenH323JNIWavFileChannel::OpenH323JNIWavFileChannel(
	const PString & fileName, H323Connection & h323Connection
	) : connection(h323Connection)
{
	wavFile.Open(fileName, PFile::ReadOnly);

	if (!wavFile.IsOpen()) 
    {
        PError << "Failed to open WAV file " << fileName << endl;
        connection.ClearCall();
    } 
	else
	{
		if (wavFile.GetFormat() != PWAVFile::fmt_PCM 
			|| wavFile.GetChannels() != 1
			|| wavFile.GetSampleRate() != 8000
			|| wavFile.GetSampleSize() != 16)
		{
			PError << "WAV file " << fileName << " has wrong format." << endl;
			wavFile.Close();
			connection.ClearCall();
		}
	}
}

BOOL OpenH323JNIWavFileChannel::Close()
{
	return wavFile.Close();
}

BOOL OpenH323JNIWavFileChannel::IsOpen() const
{
	return wavFile.IsOpen();
}

BOOL OpenH323JNIWavFileChannel::Read(void * buffer, PINDEX length)
{
    if (!connection.IsEstablished()) 
    {
        memset(buffer, 0, length);
        lastReadCount = length; 
		readDataDelay.Delay(lastReadCount/2/8); 
        return true; 
    } 
    
	if (!wavFile.Read(buffer, length)) 
            return false;
            
    lastReadCount = wavFile.GetLastReadCount(); 
    readDataDelay.Delay(lastReadCount/2/8); 
    
	if (lastReadCount < length) 
    {
		connection.ClearCall(); 
    } 
    
    return lastReadCount > 0; 
}

BOOL OpenH323JNIWavFileChannel::Write(const void * buffer, PINDEX length)
{
    lastWriteCount = length; 
    writeDataDelay.Delay(length/2/8); 
    return true;
}