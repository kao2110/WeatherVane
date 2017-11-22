package nyc.insideout.weathervane.dagger;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Scope used to declare dependencies which aren't expected to exist beyond the lifetime
 * of an Activity. The name is only descriptive. What matters is that the same annotation is
 * assigned to modules and the components they are associated with. This way when the component dies
 * so will the objects that they expose.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
