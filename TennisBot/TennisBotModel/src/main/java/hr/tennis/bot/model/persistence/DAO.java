package hr.tennis.bot.model.persistence;

import hr.tennis.bot.model.entity.tournament.Tournament;

import java.io.IOException;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author toni
 *
 */
public class DAO {

	private static final Log log = LogFactory.getLog(DAO.class);

	/**
	 * Save entity object to database.
	 *
	 * @param obj
	 *            Entity object.
	 */
	public static void save(Object obj) {
		log.debug("saving "+obj);
		PersistenceUtil.getEntityManager().persist(obj);
	}

	/**
	 * Update entity object in database.
	 *
	 * @param obj
	 *            Entity object.
	 */
	public static void update(Object obj) {
		log.debug("updating "+obj);
		PersistenceUtil.getEntityManager().merge(obj);
	}

	/**
	 * Delete entity object from database.
	 *
	 * @param obj
	 *            Entity object.
	 */
	public static void delete(Object obj) {
		log.debug("deleting "+obj);
		PersistenceUtil.getEntityManager().remove(obj);
	}

	private static Query createNamedQuery(String queryName,Map<String,Object> params){
		Query q = PersistenceUtil.getEntityManager().createNamedQuery(queryName);
		if(params!=null){
			for(Map.Entry<String,Object> entry : params.entrySet()){
				q.setParameter(entry.getKey(),entry.getValue());
			}
		}
		return q;
	}

	public static Object getSingleResult(String queryName,Map<String,Object> params) {
		Query q = createNamedQuery(queryName, params);
		try{
			return q.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}

	public static Object getResultList(String queryName,Map<String,Object> params) {
		Query q = createNamedQuery(queryName, params);
		return q.getResultList();
	}

	public static void main(String[] args) throws IOException {

		PersistenceUtil.initialize();
		PersistenceUtil.getEntityManager();

		Tournament t = (Tournament)PersistenceUtil
		.getEntityManager().createQuery("select t from Tournament t where t.id = 1055").getSingleResult();
		t.printSchedule();

		PersistenceUtil.getEntityManager().close();
		PersistenceUtil.close();
	}
}
