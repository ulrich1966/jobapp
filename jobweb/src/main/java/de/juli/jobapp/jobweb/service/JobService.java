package de.juli.jobapp.jobweb.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.controller.SourceController;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.model.Source;
import de.juli.jobapp.jobmodel.service.PersistService;
import de.juli.jobapp.jobweb.viewmodel.CurrentData;

public class JobService implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(JobService.class);

	public JobService() {
		super();
	}

	public CurrentData newEmptyData() {
		CurrentData data = new CurrentData();
		return data;
	}

	public Job writeInDb(CurrentData data, Account account) throws Exception {
		PersistService controller = new PersistService();
		Job job = data.getJob();
		account.addJob(job);
		Job persist = controller.persist(job);
		return persist;
	}

	public Job update(CurrentData data) throws Exception {
		return update(data.getJob());
	}

	public Job update(Job model) throws Exception {
		PersistService controller = new PersistService();
		Job persist = controller.persist(model);
		return persist;
	}

	public Map<Long, CurrentData> getCurrentJobs(Account account) {
		Map<Long, CurrentData> datas = new HashMap<>();
		List<Job> models = account.getJobs();
		try {
			if (null == models || models.size() == 0) {
				throw new EntityNotFoundException("Keine Datensaetze gefunden");
			}
			for (Job model : models) {
				Long id = model.getId();
				LOG.debug("Model mit Id: {} hinzufuegen", id);
				datas.put(model.getId(), new CurrentData(model));
			}
		} catch (EntityNotFoundException e) {
			LOG.error("{}", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	public List<Source> getSources() {
		SourceController sc = new SourceController();
		return sc.findAll();
	}

}
