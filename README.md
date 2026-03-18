# Ejercicio PRHTA Practico

Ejercicio practico basado en el portal PRHTA: Maven multimodulo, herencia JPA, i18n, singleton.

## Estructura

```
ejercicio-prhta-practico/
  ejercicio-base/     (JAR) Entidades, JpaController
  ejercicio-web/     (WAR) Controllers, vistas JSF, config
```

## Requisitos

- JDK 17+
- Maven 3.8+
- Payara 6+

## Configuracion en Payara

### 1. Crear pool JDBC y recurso
```

**SQL Server:**
```bash
asadmin create-jdbc-connection-pool \
  --datasourceclassname com.microsoft.sqlserver.jdbc.SQLServerDataSource \
  --restype javax.sql.DataSource \
  --property "serverName=localhost:port=1433:databaseName=EjercicioDB:user=sa:password=YourPassword" \
  EjercicioDocumentos-Pool

asadmin create-jdbc-resource \
  --connectionpoolid EjercicioDocumentos-Pool \
  jdbc/EjercicioDocumentos
```

### 2. Crear tablas

Ejecutar el script `db/create-tables-sqlserver.sql` segun tu BD.

## Compilar y desplegar

```bash
cd ejercicio-prhta-practico
mvn clean package
asadmin deploy ejercicio-web/target/ejercicio-web.war
```

Acceder: `http://localhost:8080/ejercicio-web/`

## Contenido didactico

- **Maven multimodulo**: base (JAR) + web (WAR)
- **Herencia JPA**: EntityBase -> AbstractDocument -> DocumentoInterno
- **Template Method**: getDescripcionCompleta() en abstracto
- **EclipseLink**: persistence.xml, datasource JNDI
- **i18n**: properties (en_US, es_PR), resource-bundle en faces-config
- **Singleton**: ApplicationManager (JPA, EMF, EM) como en el portal

## Ejercicios (3 tareas clave)

### Tarea 1) Herencia JPA (SINGLE_TABLE + discriminador `JPADC`)

**Objetivo**: entender cómo un cambio en el abstracto afecta a todos los hijos, y cómo el discriminador controla el tipo concreto que EclipseLink materializa.

**Instrucciones**

1. Crear un nuevo tipo `DocumentoExterno` que extienda `AbstractDocument`.
2. Configurar `@DiscriminatorValue("EXTERNO")`.
3. Agregar 1–2 campos propios (por ejemplo `proveedor` y/o `numeroReferencia`) con sus `@Column`.
4. Actualizar el script `db/create-tables-sqlserver.sql` agregando las columnas nuevas en `TECDocument` (al ser `SINGLE_TABLE`, todo vive en la misma tabla).
5. Ajustar la UI para poder crear `DocumentoInterno` y `DocumentoExterno` (puede ser por pantalla separada, o por un selector de “tipo” que inicialice el `selected` con la clase correcta).
6. Insertar datos de ambos tipos y confirmar que en SQL Server:
   - la tabla sigue siendo `TECDocument`
   - la columna `JPADC` contiene `INTERNO` o `EXTERNO`

**Notas clave (impacto en la lista)**

- Si la lista está tipada a `List<DocumentoInterno>` y filtra con `instanceof DocumentoInterno`, **los `EXTERNO` no saldrán**.
- Si cambias el listado a `List<AbstractDocument>`, **saldrán ambos**. Los campos específicos (ej. `Departamento`) requieren manejo (condicional o vía método polimórfico).

### Tarea 2) Template Method en el abstracto (flujo fijo, pasos variables)

**Objetivo**: practicar el patrón en el que el padre define el flujo y los hijos solo implementan los pasos (muy común en el portal).

**Instrucciones**

1. Convertir `getDescripcionCompleta()` en un Template Method que llame a pasos protegidos:
   - `protected abstract String getPrefijoDescripcion();`
   - `protected abstract String getDetalleDescripcion();`
2. En `AbstractDocument.getDescripcionCompleta()` construir el texto final usando esos pasos y los campos comunes (`nombre`, `estado`, etc.).
3. Implementar los pasos en cada hijo (`DocumentoInterno`, `DocumentoExterno`).
4. Probar: cambia el formato en el padre y verifica que afecta a ambos tipos sin tocar los hijos.

**Notas clave (qué cambia si modificas padre vs hijo)**

- Cambiar el **flujo** o formato en el padre impacta a **todos** los hijos.
- Cambiar la **firma** de un método abstracto rompe compilación en todos los hijos (te obliga a alinear implementaciones).
- Cambiar solo la implementación en un hijo afecta solo ese tipo.

### Tarea 3) Polimorfismo en UI/Controller sin `instanceof` (Open/Closed)

**Objetivo**: evitar lógica frágil en controllers/JSF y permitir que la UI soporte nuevos tipos sin tocar el controller.

**Instrucciones**

1. Cambiar el listado para trabajar con `List<AbstractDocument>` en lugar de `List<DocumentoInterno>`.
2. Agregar en `AbstractDocument` un método para la UI, por ejemplo:
   - `public abstract String getDetalleListado();`
3. Implementar `getDetalleListado()` en cada hijo (ej. `Departamento` para interno, `Proveedor` para externo).
4. En `list.xhtml`, renderizar el “detalle” con `#{doc.detalleListado}` y evitar `instanceof`.
5. Agregar un nuevo tipo (otra subclase) y confirmar que el controller/listado no necesita cambios, solo la nueva clase.

