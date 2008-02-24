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
		PString wavFileName = OpenH323JavaObject::GetFileNameForConnection();
		OpenH323JNIWavFileChannel *wavFileChannel = new OpenH323JNIWavFileChannel(
			wavFileName, connection);
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

void OpenH323JNIEndPoint::OnConnectionCleared(H323Connection & connection, 
		const PString & token)
{
	OpenH323JavaObject::OnDisconnected(token);
}
