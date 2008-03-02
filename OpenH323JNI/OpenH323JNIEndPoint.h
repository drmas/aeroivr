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

#ifndef _OPENH323JNIENDPOINT_H_
#define _OPENH323JNIENDPOINT_H_

#include <h323.h>
#include "OpenH323JNIConstants.h"
#include "OpenH323JNIWavFileChannel.h"

class OpenH323JNIEndPoint : public H323EndPoint
{
	PCLASSINFO(OpenH323JNIEndPoint, H323EndPoint);

private:

	OpenH323JNIWavFileChannel * GetWavFileChannelByConnection(
		const PString & connectionToken);

public:
	OpenH323JNIEndPoint();
	~OpenH323JNIEndPoint();

	BOOL Initialise();

	// Overrides from H323EndPoint
	virtual H323Connection * CreateConnection(unsigned callReference);
    virtual void OnConnectionEstablished(H323Connection & connection, 
		const PString & token);
	virtual H323Connection::AnswerCallResponse OnAnswerCall(H323Connection &, 
		const PString &, const H323SignalPDU &, H323SignalPDU &);
	virtual BOOL OpenAudioChannel(H323Connection &, BOOL, unsigned, 
		H323AudioCodec &);
    virtual void OnUserInputTone(H323Connection & connection,
      char tone, unsigned duration, unsigned logicalChannel,
      unsigned rtpTimestamp);
    virtual void OnUserInputString(
      H323Connection & connection, const PString & value);
    virtual void OnConnectionCleared(H323Connection & connection, 
		const PString & token);

	void SetNextWavFile(const PString & connectinToken, 
		const PString & wavFileName);
	void CloseConnection(const PString & connectinToken);
};

#endif; // _OPENH323JNIENDPOINT_H_
