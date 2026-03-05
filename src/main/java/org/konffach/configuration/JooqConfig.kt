package org.konffach.configuration

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.RenderNameCase
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
open class JooqConfig {

    @Bean
    open fun getDsl(dataSource: DataSource): DSLContext = DSL.using(dataSource, SQLDialect.POSTGRES, getDslSettings());

    private fun getDslSettings(): Settings = Settings().withRenderNameCase(RenderNameCase.LOWER);
}