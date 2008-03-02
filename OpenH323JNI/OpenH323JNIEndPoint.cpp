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
#include "OpenH323JNIEndPoint.h"
#include "OpenH323JavaObject.h"
#include "OpenH323JNIWavFileChannel.h"
#include "OpenH323JNINullDataChannel.h"

OpenH323JNIEndPoint::OpenH323JNIEndPoint()
{
}

OpenH323JNIEndPoint::~OpenH323JNIEndPoint()
{
}

BOOL OpenH323JNIEndPoint::Initialise()
{
	SetSilenceDetectionMode(H323AudioCodec::AdaptiveSilenceDetection);
	DisableFastStart(false);
	DisableH245Tunneling(false);

	AddAllCapabilities(0, 0, "*");
	AddAllUserInputCapabilities(0, 1);

	return true;
}

H323Connection * OpenH323JNIEndPoint::CreateConnection(unsigned callReference)
{
	return new H323Connection(*this, callReference);
}

void OpenH323JNIEndPoint::OnConnectionEstablished(H323Connection & connection, 
		const PString & token)
{
	OpenH323JavaObject::OnConnected(token);
}

H323Connection::AnswerCallResponse OpenH323JNIEndPoint::OnAnswerCall(
	H323Connection &, const PString &, const H323SignalPDU &, H323SignalPDU &)
{
	return H323Connection::AnswerCallNow;
}

BOOL OpenH323JNIEndPoint::OpenAudioChannel(H323Connection & connection, 
										   BOOL isEncoding, 
										   unsigned bufferSize,
										   H323AudioCodec & codec)
{
	codec.SetSilenceDetectionMode(H323AudioCodec::NoSilenceDetection); 
	if (isEncoding)
	{
		OpenH323JNIWavFileChannel * wavFileChannel = 
			new OpenH323JNIWavFileChannel(connection);
		return codec.AttachChannel(wavFileChannel, true); 
	}
	else
	{
		OpenH323JNINullDataChannel *nullDataChannel = new OpenH323JNINullDataChannel();
		return codec.AttachChannel(nullDataChannel, true);
	}

	return false;
}

void OpenH323JNIEndPoint::OnUserInputTone(H323Connection & connection,
      char tone, unsigned duration, unsigned logicalChannel,
      unsigned rtpTimestamp)
{
	OpenH323JavaObject::OnDtmf(connection.GetCallToken(), tone);
}

void OpenH323JNIEndPoint::OnUserInputString(H323Connection & connection, 
											const PString & value)
{
	OpenH323JavaObject::OnDtmf(connection.GetCallToken(), value.GetAt(0));
}

void OpenH323JNIEndPoint::OnConnectionCleared(H323Connection & connection, 
		const PString & token)
{
	OpenH323JavaObject::OnDisconnected(token);
}

OpenH323JNIWavFileChannel * OpenH323JNIEndPoint::GetWavFileChannelByConnection(
	const PString & connectionToken)
{
	H323Connection * connection = this->FindConnectionWithoutLocks(
		connectionToken);

	if (NULL != connection)
	{
		H323Channel * logicalChannel = connection->FindChannel(
			RTP_Session::DefaultAudioSessionID, false); 

		H323Codec * codec = logicalChannel->GetCodec();
		PChannel * rawChannel = codec->GetRawDataChannel();
		if (rawChannel->IsClass("OpenH323JNIWavFileChannel")) 
		{
			OpenH323JNIWavFileChannel * wavFileChannel = 
				(OpenH323JNIWavFileChannel*)rawChannel;
			return wavFileChannel;
		}
	}
	return NULL;
}


void OpenH323JNIEndPoint::SetNextWavFile(const PString & connectinToken, 
										 const PString & wavFileName)
{
	OpenH323JNIWavFileChannel * wavFileChannel = 
		(OpenH323JNIWavFileChannel*)GetWavFileChannelByConnection(
		connectinToken);
	if (NULL != wavFileChannel)
	{
		wavFileChannel->AddFileNameToPlay(wavFileName);
	}
}

void OpenH323JNIEndPoint::CloseConnection(const PString & connectinToken)
{
	OpenH323JNIWavFileChannel * wavFileChannel = 
		(OpenH323JNIWavFileChannel*)GetWavFileChannelByConnection(
		connectinToken);
	if (NULL != wavFileChannel)
	{
		wavFileChannel->CloseChannelAfterLastWavFile();
	}
}
