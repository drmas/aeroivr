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

JavaVM * OpenH323JavaObject::m_javaVM;
jobject  OpenH323JavaObject::m_thisObject;

void OpenH323JavaObject::SetJvmAndThisPointers(JavaVM * jvm, 
											   jobject thisObj)
{
	m_javaVM = jvm;
	m_thisObject = thisObj;
}

const char* OpenH323JavaObject::GetFileNameForConnection()
{
	JNIEnv * oEnvinronment;
	m_javaVM->AttachCurrentThread((void **)&oEnvinronment, NULL);

	jclass cls = oEnvinronment->GetObjectClass(m_thisObject);
	jmethodID methodId = oEnvinronment->GetMethodID(cls, 
		"getWavFileName", "()Ljava/lang/String;");

	jstring result = (jstring) oEnvinronment->CallObjectMethod(
		m_thisObject, methodId);

	return oEnvinronment->GetStringUTFChars(result, NULL);
}

