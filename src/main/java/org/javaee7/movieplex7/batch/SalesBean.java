/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.movieplex7.batch;

import java.util.List;
import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.operations.JobStartException;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.javaee7.movieplex7.entities.Sales;

/**
 *
 * @author ksilh
 */
@Named
@RequestScoped
public class SalesBean {

    @PersistenceUnit
    EntityManagerFactory emf;

    public List<Sales> getSalesData() {
        return emf.
                createEntityManager().
                createNamedQuery("Sales.findAll", Sales.class).
                getResultList();
    }

    public void runJob() {
        try {
            JobOperator jo = BatchRuntime.getJobOperator();
            long jobId = jo.start("eod-sales", new Properties());
            System.out.println("Started job: with id: " + jobId);
        } catch (JobStartException ex) {
            ex.printStackTrace();
        }
    }
}
