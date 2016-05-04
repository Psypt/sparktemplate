package pt.ubiquity.sparktemplate.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pt.ubiquity.sparktemplate.ServerStart;
import pt.ubiquity.sparktemplate.exception.NotFoundException;
import pt.ubiquity.sparktemplate.model.SysUser;
import pt.ubiquity.sparktemplate.util.Utils;

public class UserDAO {

	public UserDAO(){
		
	}
	
	private EntityManager getEntityManager(){
		return ServerStart.persistenceUnit.getEntityManager();
	}
	
	public String login(SysUser user){
		CriteriaBuilder qb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<SysUser> criteria = qb.createQuery(SysUser.class);
		Root<SysUser> p = criteria.from(SysUser.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(user.getUsername() != null){
			predicates.add(qb.equal(p.get("username"), user.getUsername()));
		}
		if(user.getPassword() != null){
			predicates.add(qb.equal(p.get("password"), Utils.sha256(user.getPassword())));
		}
		criteria.select(p).where(predicates.toArray(new Predicate[]{}));
		SysUser res = getEntityManager().createQuery(criteria).getSingleResult();
		String token = null;
		if(res != null){
			token = UUID.randomUUID().toString();
			res.setToken(token);
		}
	    return token;
	}
	
	public void add(SysUser user){
		 getEntityManager().getTransaction().begin();
		 try{
			 user.setId(UUID.randomUUID().toString());
			 user.setPassword(Utils.sha256(user.getPassword()));
			 Date date= new Date();
			 user.setCreationDate(new Timestamp(date.getTime()));
			 getEntityManager().persist(user);
			 getEntityManager().getTransaction().commit();
		 }catch(Exception e){
			 getEntityManager().getTransaction().rollback();
		 }
	}
	
	public void delete(String id){
		 getEntityManager().getTransaction().begin();
		 try{
			 SysUser user = getEntityManager().find(SysUser.class, id);
			 if(user == null){
				 throw new NotFoundException("User not found.");
			 }
			 if(user != null){
				 getEntityManager().remove(user);
				 getEntityManager().getTransaction().commit();
			 }
		 }catch(Exception e){
			 getEntityManager().getTransaction().rollback();
		 }
	}
	
	public void update(SysUser user) throws NotFoundException{
		 SysUser userOld = getEntityManager().find(SysUser.class, user.getId());
		 if(userOld == null){
			 throw new NotFoundException("User not found.");
		 }
		 getEntityManager().getTransaction().begin();
		 try{
			 if(user != null){
				 user.setPassword(Utils.sha256(user.getPassword()));
				 user.setCreationDate(userOld.getCreationDate());
				 getEntityManager().merge(user);
				 getEntityManager().getTransaction().commit();
			 }
		 }catch(Exception e){
			 getEntityManager().getTransaction().rollback();
		 }
	}
	
	public SysUser getById(String id){
		return getEntityManager().find(SysUser.class, id);
	}
	
	public List<SysUser> getAll(){
		CriteriaQuery<SysUser> criteria = getEntityManager().getCriteriaBuilder().createQuery(SysUser.class);
	    criteria.select(criteria.from(SysUser.class));
	    List<SysUser> users = getEntityManager().createQuery(criteria).getResultList();
	    return users;
	}
	
}
