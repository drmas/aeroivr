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

#include "OpenH323JNIApplication.h"
#include "OpenH323JNIConstants.h";

OpenH323JNIApplication::OpenH323JNIApplication()
{
	PTrace::SetLevel(TRACE_DETAILS);
	m_pOpenH323JNIEndPoint = new OpenH323JNIEndPoint();
}

OpenH323JNIApplication::~OpenH323JNIApplication()
{
	if (NULL != m_pOpenH323JNIEndPoint)
	{
		delete m_pOpenH323JNIEndPoint;
	}
}

OpenH323JNIEndPoint* OpenH323JNIApplication::GetH323EndPoint()
{
	return m_pOpenH323JNIEndPoint;
}
