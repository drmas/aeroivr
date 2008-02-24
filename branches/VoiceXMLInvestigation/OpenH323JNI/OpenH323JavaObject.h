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

#ifndef _OPENH323JAVAOBJECT_H_
#define _OPENH323JAVAOBJECT_H_

#include <jni.h>

class OpenH323JavaObject
{
private:
	static JavaVM * m_javaVM;
	static jobject m_thisObject;

	static void CallVoidMethodWithConnectionToken(
		const char * methodName, const PString & connectionToken);

public:
	static void SetJvmAndThisPointers(JavaVM * jvm, jobject thisObj);

	static void OnConnected(const PString & connectionToken);
	static void OnDisconnected(const PString & connectionToken);
	static void OnDtmf(const PString & connectionToken, const char dtmf);
};

#endif // _OPENH323JAVAOBJECT_H_
