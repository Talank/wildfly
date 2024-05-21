/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.wildfly.extension.clustering.server.registry;

import org.kohsuke.MetaInfServices;
import org.wildfly.clustering.server.service.ClusteredCacheServiceInstallerProvider;

/**
 * @author Paul Ferraro
 */
@MetaInfServices(ClusteredCacheServiceInstallerProvider.class)
public class CacheRegistryFactoryServiceInstallerProvider<K, V> extends RegistryFactoryServiceInstallerProvider<K, V> implements ClusteredCacheServiceInstallerProvider {

    public CacheRegistryFactoryServiceInstallerProvider() {
        super(new CacheRegistryFactoryServiceInstallerFactory<>());
    }
}
