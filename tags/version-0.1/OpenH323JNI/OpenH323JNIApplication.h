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

#ifndef _OPENH323JNIAPPLICATION_H
#define _OPENH323JNIAPPLICATION_H

#include "OpenH323JNIProcess.h"
#include "OpenH323JNIEndPoint.h"
#include "OpenH323JNIConstants.h"

class OpenH323JNIApplication
{
private:
	OpenH323JNIProcess m_oOpenH323JNIProcess;
	OpenH323JNIEndPoint * m_pOpenH323JNIEndPoint;

public:
	OpenH323JNIApplication();
	~OpenH323JNIApplication();

	OpenH323JNIEndPoint* GetH323EndPoint() const;
};

#endif // _OPENH323JNIAPPLICATION_H_
