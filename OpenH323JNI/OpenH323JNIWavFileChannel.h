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

#ifndef _OPENH323JNIWAVFILECHANNEL_H_
#define _OPENH323JNIWAVFILECHANNEL_H_

#include <ptlib.h>
#include <h323.h>
#include <ptclib/pwavfile.h>
#include <ptclib/delaychan.h>
#include "OpenH323JNIConstants.h"
#include "OpenH323SynchronizedQueue.h"

class OpenH323JNIWavFileChannel: public PIndirectChannel 
{
	PCLASSINFO(OpenH323JNIWavFileChannel, PIndirectChannel);

	H323Connection & connection;
	PWAVFile wavFile;
	BOOL isOpened;
	BOOL closeChannel;
	PAdaptiveDelay writeDataDelay;
	PAdaptiveDelay readDataDelay;
	
	OpenH323SynchronizedQueue wavFileNames;

public:
	OpenH323JNIWavFileChannel(H323Connection &);
	void AddFileNameToPlay(const PString & fileName);
	void CloseChannelAfterLastWavFile();

	virtual BOOL Close(); 
	virtual BOOL IsOpen() const;
	virtual BOOL Read(void *, PINDEX);
	virtual BOOL Write(const void *, PINDEX); 

private: 
	BOOL OpenWavFile(const PString & wavFileName);
	void OpenNextWavFileFromQueue();
};

#endif // _OPENH323JNIWAVFILECHANNEL_H_