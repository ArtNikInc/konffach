package org.konffach.persistance.repository

import org.jooq.DSLContext


abstract class AbstractRepository(protected val dsl: DSLContext) {
}