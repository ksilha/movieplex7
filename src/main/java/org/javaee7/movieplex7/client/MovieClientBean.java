/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.movieplex7.client;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.javaee7.movieplex7.entities.Movie;
import org.javaee7.movieplex7.json.MovieWriter;

/**
 *
 * @author ksilh
 */
@Named
@RequestScoped
public class MovieClientBean {

    Client client;
    WebTarget target;

    @Inject
    HttpServletRequest httpServletRequest;

    @Inject
    MovieBackingBean bean;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client
                .target("http://"
                        + httpServletRequest.getLocalName()
                        + ":"
                        + httpServletRequest.getLocalPort()
                        + httpServletRequest.getContextPath()
                        + "/webresources/movie/");
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public Movie[] getMovies() {
        return target
                .request()
                .get(Movie[].class);
    }

    public Movie getMovie() {
        Movie m = target
                .path("{movie}")
                .resolveTemplate("movie", bean.getMovieId())
                .request()
                .get(Movie.class);
        return m;
    }

    public void deleteMovie() {
        target
                .path("{movieId}")
                .resolveTemplate("movieId", bean.getMovieId())
                .request()
                .delete();
    }

    public void addMovie() {
        Movie m = new Movie();
        m.setId(bean.getMovieId());
        m.setName(bean.getMovieName());
        m.setActors(bean.getActors());
        target
                .register(MovieWriter.class)
                .request()
                .post(Entity.entity(m, MediaType.APPLICATION_JSON));
    }
}
