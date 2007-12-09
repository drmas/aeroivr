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

#ifndef _OPENH323JNINULLDATACHANNEL_H_
#define _OPENH323JNINULLDATACHANNEL_H_

#include <ptlib.h>
#include <h323.h>
#include <ptclib/delaychan.h>
#include "OpenH323JNIConstants.h"

class OpenH323JNINullDataChannel: public PIndirectChannel
{
  PCLASSINFO(OpenH323JNINullDataChannel, PIndirectChannel);
  
  PAdaptiveDelay readDataDelay;
  PAdaptiveDelay writeDataDelay;
  bool isOpen;

public:
    OpenH323JNINullDataChannel();

    virtual BOOL Close(); 
    virtual BOOL IsOpen() const;
    virtual BOOL Read(void *, PINDEX);
    virtual BOOL Write(const void *, PINDEX); 
};

#endif // _OPENH323JNINULLDATACHANNEL_H_