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
#include "OpenH323JNIUtils.h"

void OpenH323JNIUtils::JStringToPString(JNIEnv * envinronment, 
										jstring & source, 
										PString & destination)
{
	char * buffer;
	int len = envinronment->GetStringLength(source);
	
	buffer = new char[len + 1];

	envinronment->GetStringUTFRegion(source, 0, len, buffer);
	destination = buffer;

	delete[] buffer;
}


