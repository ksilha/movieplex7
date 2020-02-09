/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.movieplex7.batch;

import java.util.List;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.javaee7.movieplex7.entities.Sales;

/**
 *
 * @author ksilh
 */
@Named
@Dependent
public class SalesWriter extends AbstractItemWriter {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void writeItems(List list) {
        for (Sales s : (List<Sales>) list) {
            em.persist(s);
        }
    }
}
