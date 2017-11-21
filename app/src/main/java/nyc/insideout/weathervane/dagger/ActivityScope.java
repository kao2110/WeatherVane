package nyc.insideout.weathervane.dagger;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Scope used to declare dependencies which aren't expected to exist beyond the lifetime
 * of an Activity.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
