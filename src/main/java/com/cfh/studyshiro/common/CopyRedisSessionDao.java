package com.cfh.studyshiro.common;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfh.studyshiro.util.RedisDb;

public class CopyRedisSessionDao extends AbstractSessionDAO{
	Logger log = LoggerFactory.getLogger(CopyRedisSessionDao.class);

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        RedisDb.setObject(sessionId.toString().getBytes(), sessionToByte(session));
        
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
    	log.info("read session");
        // 先从缓存中获取session，如果没有再去数据库中获取
        //Session session = super.doReadSession(sessionId); 
    	Session session = null;
        if(session == null){
            byte[] bytes = RedisDb.getObject(sessionId.toString().getBytes());
            if(bytes != null && bytes.length > 0){
                session = byteToSession(bytes);    
            }
        }
        return session;
    }

    // 把session对象转化为byte保存到redis中
    public byte[] sessionToByte(Session session){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(session);
            bytes = bo.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    
    // 把byte还原为session
    public Session byteToSession(byte[] bytes){
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream in;
        SimpleSession session = null;
        try {
            in = new ObjectInputStream(bi);
            session = (SimpleSession) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return session;
    }

	@Override
	public void update(Session session) throws UnknownSessionException {
		RedisDb.setObject(session.getId().toString().getBytes(), sessionToByte(session));
	}

	@Override
	public void delete(Session session) {
		RedisDb.delString(session.getId() + "");
	}

	@Override
	public Collection<Session> getActiveSessions() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
