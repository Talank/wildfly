/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.wildfly.extension.clustering.web;

import java.util.List;
import java.util.function.UnaryOperator;

import org.jboss.as.clustering.controller.ChildResourceDefinition;
import org.jboss.as.clustering.controller.ResourceDescriptor;
import org.jboss.as.clustering.controller.ResourceServiceHandler;
import org.jboss.as.clustering.controller.SimpleResourceRegistrar;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.capability.RuntimeCapability;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.wildfly.subsystem.resource.operation.ResourceOperationRuntimeHandler;
import org.wildfly.subsystem.service.ResourceServiceConfigurator;

/**
 * @author Paul Ferraro
 */
public abstract class AffinityResourceDefinition extends ChildResourceDefinition<ManagementResourceRegistration> implements ResourceServiceConfigurator {

    static PathElement pathElement(String value) {
        return PathElement.pathElement("affinity", value);
    }

    private final RuntimeCapability<Void> capability;
    private final UnaryOperator<ResourceDescriptor> configurator;

    AffinityResourceDefinition(PathElement path, RuntimeCapability<Void> capability, UnaryOperator<ResourceDescriptor> configurator) {
        super(path, DistributableWebExtension.SUBSYSTEM_RESOLVER.createChildResolver(path, pathElement(PathElement.WILDCARD_VALUE)));
        this.capability = capability;
        this.configurator = configurator;
    }

    @Override
    public ManagementResourceRegistration register(ManagementResourceRegistration parent) {
        ManagementResourceRegistration registration = parent.registerSubModel(this);
        ResourceDescriptor descriptor = this.configurator.apply(new ResourceDescriptor(this.getResourceDescriptionResolver())).addCapabilities(List.of(this.capability));
        ResourceServiceHandler handler = ResourceServiceHandler.of(ResourceOperationRuntimeHandler.configureService(this));
        new SimpleResourceRegistrar(descriptor, handler).register(registration);
        return registration;
    }
}
