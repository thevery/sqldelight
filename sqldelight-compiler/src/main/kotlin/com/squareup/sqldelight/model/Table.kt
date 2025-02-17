/*
 * Copyright (C) 2016 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.sqldelight.model

import com.squareup.javapoet.ClassName
import com.squareup.sqldelight.SqliteCompiler
import java.util.ArrayList

class Table<T>(packageName: String, name: String, val sqlTableName: String, originatingElement: T)
    : SqlElement<T>(originatingElement) {
  val columns = ArrayList<Column<T>>()
  val interfaceName = SqliteCompiler.interfaceName(name)
  val interfaceClassName = ClassName.get(packageName, interfaceName)
  val marshalClassName = interfaceClassName.nestedClass("${name}Marshal")
  val mapperClassName = interfaceClassName.nestedClass("Mapper")
  val creatorClassName = mapperClassName.nestedClass("Creator")
}
