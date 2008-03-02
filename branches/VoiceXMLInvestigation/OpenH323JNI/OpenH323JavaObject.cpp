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
#include "OpenH323JavaObject.h"
#include "OpenH323JNIConstants.h"

JavaVM * OpenH323JavaObject::m_javaVM;
jobject  OpenH323JavaObject::m_thisObject;

void OpenH323JavaObject::SetJvmAndThisPointers(JavaVM * jvm, 
											   jobject thisObj)
{
	m_javaVM = jvm;
	m_thisObject = thisObj;
}

void OpenH323JavaObject::CallVoidMethodWithConnectionToken(
	const char * methodName, const PString & connectionToken) 
{
	JNIEnv * oEnvinronment;
	m_javaVM->AttachCurrentThread((void **)&oEnvinronment, NULL);

	jclass cls = oEnvinronment->GetObjectClass(m_thisObject);
	jmethodID methodId = oEnvinronment->GetMethodID(cls, 
		methodName, "(Ljava/lang/String;)V");
	jstring connectionId = oEnvinronment->NewStringUTF((const char*) connectionToken);

	oEnvinronment->CallVoidMethod(m_thisObject, methodId, connectionId);
}

void OpenH323JavaObject::OnConnected(const PString & connectionToken)
{
	OpenH323JavaObject::CallVoidMethodWithConnectionToken(
		"onConnected", connectionToken);
}

void OpenH323JavaObject::OnDisconnected(const PString & connectionToken)
{
	OpenH323JavaObject::CallVoidMethodWithConnectionToken(
		"onDisconnected", connectionToken);
}

void OpenH323JavaObject::OnDtmf(const PString & connectionToken, const char dtmf)
{
	JNIEnv * oEnvinronment;

	jint result = m_javaVM->AttachCurrentThread((void **)&oEnvinronment, NULL);
	PTRACE(TRACE_INFORMATION, "AttachCurrentThread result is " << result);

	jclass cls = oEnvinronment->GetObjectClass(m_thisObject);
	jmethodID methodId = oEnvinronment->GetMethodID(cls, 
		"onDtmf", "(Ljava/lang/String;C)V");
	jstring connectionId = oEnvinronment->NewStringUTF((const char*) connectionToken);

	oEnvinronment->CallVoidMethod(m_thisObject, methodId, connectionId, (jchar) dtmf);
}


