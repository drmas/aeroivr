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
#include "OpenH323SynchronizedQueue.h"

void OpenH323SynchronizedQueue::EnqueueString(PString * value)
{
	operationCriticalSection.Enter();
	stringQueue.Enqueue(value);
	operationCriticalSection.Leave();
}

PString * OpenH323SynchronizedQueue::DequeueString()
{
	operationCriticalSection.Enter();
	PString * result = stringQueue.Dequeue();
	operationCriticalSection.Leave();
	return result;
}

BOOL OpenH323SynchronizedQueue::IsEmpty()
{
	operationCriticalSection.Enter();
	BOOL result = stringQueue.IsEmpty();
	operationCriticalSection.Leave();
	return result;
}

void OpenH323SynchronizedQueue::Clear()
{
	operationCriticalSection.Enter();
	stringQueue.RemoveAll();
	operationCriticalSection.Leave();
}
