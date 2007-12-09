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

#include "OpenH323JNINullDataChannel.h"

OpenH323JNINullDataChannel::OpenH323JNINullDataChannel()
{
	isOpen = true;
}

BOOL OpenH323JNINullDataChannel::Close()
{
	isOpen = false;
	return true;
}

BOOL OpenH323JNINullDataChannel::IsOpen() const 
{ 
	return isOpen; 
}

BOOL OpenH323JNINullDataChannel::Read(void * buffer, PINDEX length)
{
	memset(buffer, 0, length);
    lastReadCount = length;
	readDataDelay.Delay(length/2/8);
    return true;    
}

BOOL OpenH323JNINullDataChannel::Write(const void * buffer, PINDEX length)
{
	lastWriteCount = length;
	writeDataDelay.Delay(length/2/8);
    return true;

}