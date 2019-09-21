package service;

import model.MovieLibrary;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * define base URI und all provider classes
 *
 * M133: Bookshelf_05
 *
 * @author Leo Ferrari
 * @since 2019-09-21
 * @version 1.0
 */

@ApplicationPath("/resource")

public class MyApplication extends Application {

    /**
     * define all provider classes
     * @return set of classes
     */
    @Override
    public Set<Class<?>> getClasses() {
        HashSet providers = new HashSet<Class<?>>();
        providers.add( MovieLibraryService.class );
        providers.add( UserService.class);
        return providers;
    }
}