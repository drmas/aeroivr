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

#ifndef _OPENH323JNIPROCESS_H
#define _OPENH323JNIPROCESS_H

#include <h323.h>
#include "OpenH323JNIConstants.h"


class OpenH323JNIProcess :
	public PProcess
{
	PCLASSINFO(OpenH323JNIProcess, PProcess)

public:
	void Main();
};

#endif  // _OPENH323JNIPROCESS_H
