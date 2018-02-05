package hr.tennis.bot.model.persistence;

import hr.tennis.bot.model.Constants;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author toni
 *
 */
public class PersistenceUtil {

	public static final Log log = LogFactory.getLog(PersistenceUtil.class);

	private static class Data {
		EntityManager em;
		EntityTransaction tx;
	}

	private static EntityManagerFactory emf = null;
	private static String persistenceUnit = null;
	private static ThreadLocal<Data> threadLocal = new ThreadLocal<Data>();

	public static EntityManagerFactory initialize(){
		return initialize(Constants.PERSISTENCE_UNIT);
	}

	public static EntityManagerFactory initialize(String persistenceUnit){
		if(emf==null){
			PersistenceUtil.emf = Persistence.createEntityManagerFactory(persistenceUnit);
			PersistenceUtil.persistenceUnit = persistenceUnit;
			log.info(String.format("emf %s initialized",persistenceUnit));
		}
		return PersistenceUtil.emf;
	}

	public static void close(){
		log.info(String.format("emf %s closed",PersistenceUtil.persistenceUnit));
		PersistenceUtil.emf.close();
	}


	//	public static <T> T executeSingleDatabaseOperation(DatabaseOperation<T> dbo) {
	//		T result = null;
	//		EntityManager em = PersistenceUtil.getEntityManager();
	//		try {
	//			PersistenceUtil.beginTransaction();
	//			result = dbo.executeOperation(em);
	//			PersistenceUtil.commitTransaction();
	//		} catch(RuntimeException ex) {
	//			PersistenceUtil.rollbackIfNeeded();
	//			throw ex;
	//		} finally {
	//			PersistenceUtil.closeEntityManager();
	//		}
	//		return result;
	//	}
	//
	//	public static <T> T executeSingleDatabaseOperation(DatabaseOperationExt<T> dbo) {
	//		T result = null;
	//		EntityManager em = PersistenceUtil.getEntityManager();
	//		try {
	//			PersistenceUtil.beginTransaction();
	//			result = dbo.executeOperationExt(em, PersistenceUtil.getEntityTransaction());
	//			PersistenceUtil.commitTransaction();
	//		} catch(RuntimeException ex) {
	//			PersistenceUtil.rollbackIfNeeded();
	//			throw ex;
	//		} finally {
	//			PersistenceUtil.closeEntityManager();
	//		}
	//		return result;
	//	}
	//

	public static EntityManager getEntityManager() {
		Data d = threadLocal.get();
		if(d != null) {
			return d.em;
		}
		if(emf == null) {
			throw new IllegalStateException("Persistence provider is not available.");
		}
		d = new Data();
		d.em = emf.createEntityManager();
		log.debug(String.format("em %s created",d.em));
		try {
			d.tx = d.em.getTransaction();
		} catch(RuntimeException ex) {
			d.em.close();
			throw ex;
		}
		threadLocal.set(d);
		return d.em;
	}

	public static void closeEntityManager() {
		Data d = threadLocal.get();
		if(d == null) {
			return;
		}
		d.em.close();
		log.debug(String.format("em %s closed",d.em));
		threadLocal.remove();
	}

	private static EntityTransaction getEntityTransaction() {
		Data d = threadLocal.get();
		if(d == null) {
			return null;
		}
		return d.tx;
	}

	public static void rollbackIfNeeded() {
		Data d = threadLocal.get();
		if(d == null) {
			return;
		}
		if(d.tx.isActive()) {
			d.tx.rollback();
			log.debug(String.format("transaction %s rollbacked",d.tx));
		}
	}

	public static boolean isTransactionActive() {
		Data d = threadLocal.get();
		if(d == null) {
			throw new IllegalStateException("Entity manager is not accessible. Are you calling PersistenceUtil.isTransactionActive() before PersistenceUtil.getEntityManager()?");
		}
		return d.tx.isActive();
	}

	public static void beginTransaction() {
		Data d = threadLocal.get();
		if(d == null) {
			throw new IllegalStateException("Entity manager is not accessible. Are you calling PersistenceUtil.beginTransaction() before PersistenceUtil.getEntityManager()?");
		}
		d.tx.begin();
		log.debug(String.format("transaction %s begun",d.tx));
	}

	public static void commitTransaction() {
		Data d = threadLocal.get();
		if(d == null) {
			throw new IllegalStateException("Entity manager is not accessible. Are you calling PersistenceUtil.commitTransaction() before PersistenceUtil.getEntityManager()?");
		}
		d.tx.commit();
		log.debug(String.format("transaction %s commited",d.tx));
	}

	public static void rollbackTransaction() {
		Data d = threadLocal.get();
		if(d == null) {
			throw new IllegalStateException("Entity manager is not accessible. Are you calling PersistenceUtil.rollbackTransaction() before PersistenceUtil.getEntityManager()?");
		}
		d.tx.rollback();
		log.debug(String.format("transaction %s rollbacked",d.tx));
	}
}
