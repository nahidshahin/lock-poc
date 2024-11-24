package com.h1.vm;

import org.mule.runtime.api.store.ObjectStoreException;

//import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import java.util.List;
import java.util.concurrent.locks.Lock;

import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.store.ObjectStore;
import org.mule.runtime.api.store.ObjectStoreException;
import org.mule.runtime.core.api.MuleContext;

import org.springframework.beans.factory.annotation.Autowired;

import org.mule.runtime.core.api.MuleContext;
import org.mule.runtime.core.api.util.queue.Queue;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("deprecation")
public class VmUtilities {

	@Autowired
	private MuleContext muleContext;
	String objectStoreName = "ObjectStore_Count";
	String objectStoreKey = "ObjectStore_Count_Key";

	public Integer getValue() throws ObjectStoreException {
		
		ObjectStore<Integer> objectStore = muleContext.getObjectStoreManager()
	            .getObjectStore(objectStoreName);
		Integer val = 0;
		if (objectStore.contains(objectStoreKey)) {
			val = objectStore.retrieve(objectStoreKey);
        }
		return val;
	}
	
	public void incValue() throws ObjectStoreException {
		Lock lock = muleContext.getLockFactory().createLock(objectStoreName + "Lock");
		
		ObjectStore<Integer> objectStore = muleContext.getObjectStoreManager()
	            .getObjectStore(objectStoreName);
		lock.lock();
		try {		
			Integer val = 0;
			if (objectStore.contains(objectStoreKey)) {
				val = objectStore.retrieve(objectStoreKey);
				objectStore.remove(objectStoreKey);
	        }
	        objectStore.store(objectStoreKey, val + 1);
		} finally {
			lock.unlock();
		}
	}

	public void resetValue() throws ObjectStoreException {
		Lock lock = muleContext.getLockFactory().createLock(objectStoreName + "Lock");
		
		ObjectStore<Integer> objectStore = muleContext.getObjectStoreManager()
	            .getObjectStore(objectStoreName);
		lock.lock();
		try {		
			Integer val = 0;
			if (objectStore.contains(objectStoreKey)) {
				objectStore.remove(objectStoreKey);
	        }
	        objectStore.store(objectStoreKey, val);
		} finally {
			lock.unlock();
		}
	}
	
}
