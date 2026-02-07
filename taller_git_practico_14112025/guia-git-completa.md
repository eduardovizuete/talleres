# Guía Completa de Git

## Tabla de Contenidos

- [Guía Completa de Git](#guía-completa-de-git)
  - [Tabla de Contenidos](#tabla-de-contenidos)
  - [¿Qué es Git?](#qué-es-git)
    - [Analogía Práctica](#analogía-práctica)
  - [Conceptos Fundamentales](#conceptos-fundamentales)
    - [Estados de los Archivos](#estados-de-los-archivos)
    - [Flujo de Trabajo Básico](#flujo-de-trabajo-básico)
  - [Comandos Básicos](#comandos-básicos)
    - [Configuración Inicial](#configuración-inicial)
    - [Crear y Clonar Repositorios](#crear-y-clonar-repositorios)
    - [Ciclo Básico de Trabajo](#ciclo-básico-de-trabajo)
    - [Ver Historial y Diferencias](#ver-historial-y-diferencias)
    - [Trabajar con Ramas](#trabajar-con-ramas)
    - [Trabajar con Repositorios Remotos](#trabajar-con-repositorios-remotos)
    - [Comandos Avanzados](#comandos-avanzados)
    - [Escenarios Avanzados](#escenarios-avanzados)
    - [Mejores Prácticas](#mejores-prácticas)
      - [Mensajes de Commit](#mensajes-de-commit)
      - [Estrategias de Branching](#estrategias-de-branching)
      - [Reglas de Oro](#reglas-de-oro)
      - [Comandos Útiles del Día a Día](#comandos-útiles-del-día-a-día)
  - [Tabla Resumen de Comandos](#tabla-resumen-de-comandos)
    - [Comandos Básicos](#comandos-básicos-1)
    - [Comandos Avanzados](#comandos-avanzados-1)
  - [Recursos Adicionales](#recursos-adicionales)
    - [Documentación Oficial](#documentación-oficial)
    - [Herramientas Visuales](#herramientas-visuales)
    - [Práctica Interactiva](#práctica-interactiva)
  - [Planificación y Tiempos de Ejecución](#planificación-y-tiempos-de-ejecución)
    - [📊 Estimación de Tiempo por Secciones](#-estimación-de-tiempo-por-secciones)
      - [**Nivel Básico (Escenarios 1-6)**](#nivel-básico-escenarios-1-6)
      - [**Nivel Avanzado (Escenarios 7-20)**](#nivel-avanzado-escenarios-7-20)
      - [**Mejores Prácticas y Comandos Útiles**](#mejores-prácticas-y-comandos-útiles)
    - [⏱️ Formatos de Taller Recomendados](#️-formatos-de-taller-recomendados)
      - [**Opción 1: Taller Básico (Medio Día)**](#opción-1-taller-básico-medio-día)
      - [**Opción 2: Taller Completo (1 Día)**](#opción-2-taller-completo-1-día)
      - [**Opción 3: Curso Completo (2 Días)**](#opción-3-curso-completo-2-días)
      - [**Opción 4: Curso Distribuido (4 Sesiones)**](#opción-4-curso-distribuido-4-sesiones)
    - [🎯 Factores que Afectan el Tiempo](#-factores-que-afectan-el-tiempo)
      - [**Aumentan el tiempo (+):**](#aumentan-el-tiempo-)
      - [**Reducen el tiempo (-):**](#reducen-el-tiempo--)
    - [📚 Recomendación para Autoaprendizaje](#-recomendación-para-autoaprendizaje)
    - [💡 Consejos para Maximizar el Aprendizaje](#-consejos-para-maximizar-el-aprendizaje)
    - [📋 Checklist de Preparación para Talleres](#-checklist-de-preparación-para-talleres)
      - [**Antes del Taller:**](#antes-del-taller)
      - [**Durante el Taller:**](#durante-el-taller)
      - [**Después del Taller:**](#después-del-taller)

---

## ¿Qué es Git?

**Git** es un sistema de control de versiones distribuido que funciona como una "máquina del tiempo" para tu código. Permite:

- 📸 **Guardar instantáneas** de tu proyecto en diferentes momentos
- 🔄 **Volver atrás** si algo sale mal
- 🌿 **Trabajar en paralelo** con ramas independientes
- 👥 **Colaborar** con otros desarrolladores sin conflictos
- 📊 **Rastrear cambios** y saber quién hizo qué y cuándo

### Analogía Práctica

Imagina que Git es como un álbum de fotos de tu proyecto:
- Cada **commit** es una foto que captura el estado completo
- Las **ramas** son álbumes paralelos donde experimentas sin afectar el álbum principal
- El **merge** es cuando combinas fotos de diferentes álbumes
- El **repositorio remoto** es una copia de seguridad en la nube que compartes con tu equipo

---

## Conceptos Fundamentales

### Estados de los Archivos

```
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│   Working        │     │    Staging       │     │   Repository     │
│   Directory      │────▶│    Area          │────▶│   (Commits)      │
│                  │     │                  │     │                  │
│ Archivos         │     │ Archivos         │     │ Historial        │
│ modificados      │     │ preparados       │     │ permanente       │
└──────────────────┘     └──────────────────┘     └──────────────────┘
   git add                  git commit
```

### Flujo de Trabajo Básico

1. **Modificar** archivos en tu directorio de trabajo
2. **Preparar** cambios con `git add` (staging)
3. **Confirmar** cambios con `git commit` (guardar instantánea)
4. **Compartir** con `git push` (subir al servidor)
5. **Actualizar** con `git pull` (descargar cambios de otros)

---

## Comandos Básicos

### Configuración Inicial

| Comando | Descripción | Ejemplo |
|---------|-------------|----------|
| `git config --global user.name "Nombre"` | Configura tu nombre | `git config --global user.name "María García"` |
| `git config --global user.email "email"` | Configura tu email | `git config --global user.email "maria@ejemplo.com"` |
| `git config --list` | Muestra toda la configuración | Ver todas las opciones configuradas |

**Ejemplo práctico:**

```bash
# Configurar identidad (hacer una sola vez)
git config --global user.name "María García"
git config --global user.email "maria@ejemplo.com"

# Configurar editor predeterminado
git config --global core.editor "code --wait"  # Para VS Code

# Verificar configuración
git config --list
```

---

### Crear y Clonar Repositorios

| Comando | Descripción | Ejemplo |
|---------|-------------|----------|
| `git init` | Crea un nuevo repositorio en la carpeta actual | Convierte carpeta normal en repo Git |
| `git clone <url>` | Descarga una copia completa de un repositorio | `git clone https://github.com/user/repo.git` |
| `git clone <url> <carpeta>` | Clona con nombre de carpeta personalizado | `git clone https://... mi-proyecto` |

**Escenario 1: Crear un proyecto nuevo desde cero**

```bash
# Crear carpeta del proyecto
mkdir mi-sitio-web
cd mi-sitio-web

# Inicializar repositorio Git
git init
# Salida: Initialized empty Git repository in /ruta/mi-sitio-web/.git/

# Crear primer archivo
echo "# Mi Sitio Web" > README.md
echo "<!DOCTYPE html>" > index.html

# Ver estado
git status
# Salida: Untracked files: README.md, index.html

# Preparar y guardar
git add .
git commit -m "Commit inicial: estructura básica del proyecto"
```

**Escenario 2: Clonar un proyecto existente**

```bash
# Clonar repositorio de GitHub
git clone https://github.com/facebook/react.git
cd react

# Ver historial del proyecto
git log --oneline --graph --all -10

# Ver ramas disponibles
git branch -a
```

---

### Ciclo Básico de Trabajo

| Comando | Descripción | Ejemplo |
|---------|-------------|----------|
| `git status` | Muestra el estado actual de los archivos | Ver qué archivos cambiaron |
| `git add <archivo>` | Prepara un archivo específico | `git add index.html` |
| `git add .` | Prepara todos los archivos modificados | Agregar todo de una vez |
| `git commit -m "mensaje"` | Guarda los cambios con descripción | `git commit -m "Agregué navbar"` |
| `git diff` | Muestra diferencias no preparadas | Ver cambios exactos |

**Escenario 3: Flujo de trabajo completo**

```bash
# 1. Verificar estado inicial
git status
# On branch main, nothing to commit, working tree clean

# 2. Crear y modificar archivos
echo "body { margin: 0; }" > styles.css
echo "<link rel='stylesheet' href='styles.css'>" >> index.html

# 3. Ver qué detectó Git
git status
# Untracked files: styles.css
# Modified: index.html

# 4. Ver diferencias exactas
git diff index.html
# +<link rel='stylesheet' href='styles.css'>

# 5. Preparar archivos
git add styles.css index.html

# 6. Confirmar cambios
git commit -m "Agregué estilos CSS y vinculé en HTML"

# 7. Ver el commit en el historial
git log --oneline -1
# a3f5b21 Agregué estilos CSS y vinculé en HTML
```

---

### Ver Historial y Diferencias

| Comando | Descripción | Ejemplo |
|---------|-------------|----------|
| `git log` | Muestra historial completo | Lista todos los commits |
| `git log --oneline` | Historial compacto | Una línea por commit |
| `git log --graph --all` | Historial visual con ramas | Ver ramificaciones |
| `git log --author="Nombre"` | Filtra por autor | Ver commits de una persona |
| `git blame <archivo>` | Muestra quién modificó cada línea | `git blame index.html` |
| `git show <commit>` | Muestra detalles de un commit | `git show a3f5b21` |

**Escenario 4: Investigar el historial**

```bash
# Ver últimos 5 commits de forma compacta
git log --oneline -5
# e5f6g78 Agregué validación de formulario
# d4e5f67 Creé página de contacto
# c3d4e56 Actualicé estilos del navbar
# b2c3d45 Agregué navbar responsive
# a1b2c34 Commit inicial

# Ver historial con gráfico de ramas
git log --oneline --graph --all
# * e5f6g78 (HEAD -> main) Agregué validación
# * d4e5f67 Creé página de contacto
# | * c9d8e7f (feature/blog) Agregué sistema de blog
# |/
# * c3d4e56 Actualicé estilos

# Ver quién modificó cada línea
git blame index.html
# a1b2c34 (María  2025-11-10 1) <!DOCTYPE html>
# c3d4e56 (Juan   2025-11-12 2) <nav>...</nav>
```

---

### Trabajar con Ramas

| Comando | Descripción | Ejemplo |
|---------|-------------|----------|
| `git branch` | Lista ramas locales | Muestra todas las ramas |
| `git branch <nombre>` | Crea nueva rama | `git branch nueva-funcionalidad` |
| `git branch -d <rama>` | Elimina rama (seguro) | Solo si está fusionada |
| `git checkout <rama>` | Cambia a otra rama | `git checkout desarrollo` |
| `git checkout -b <rama>` | Crea y cambia a nueva rama | Atajo útil |
| `git merge <rama>` | Fusiona rama en la actual | `git merge feature` |

**Escenario 5: Desarrollar una nueva funcionalidad**

```bash
# Situación: Tienes un sitio web funcionando en 'main'
# Quieres agregar un blog sin arriesgar el código estable

# 1. Ver rama actual
git branch
# * main

# 2. Crear rama para el blog
git checkout -b feature/blog
# Switched to a new branch 'feature/blog'

# 3. Desarrollar la funcionalidad
echo "<h1>Mi Blog</h1>" > blog.html
git add blog.html
git commit -m "Creé estructura básica del blog"

echo "<article>Primer post</article>" >> blog.html
git add blog.html
git commit -m "Agregué primer artículo"

# 4. Volver a main
git checkout main
ls  # blog.html NO está aquí

# 5. Fusionar el blog en main
git merge feature/blog
# Updating c3d4e56..f9e8d7c
# Fast-forward

# 6. Verificar que el blog ya está en main
ls  # blog.html AHORA está aquí

# 7. Eliminar rama ya fusionada
git branch -d feature/blog
```

**Visualización:**

```
Antes del merge:
main:           A---B---C
                     \
feature/blog:         D---E---F

Después del merge:
main:           A---B---C---D---E---F
```

---

### Trabajar con Repositorios Remotos

| Comando | Descripción | Ejemplo |
|---------|-------------|----------|
| `git remote -v` | Lista repositorios remotos con URLs | Ver direcciones completas |
| `git remote add <nombre> <url>` | Agrega nuevo remoto | `git remote add origin https://...` |
| `git fetch` | Descarga cambios sin fusionar | `git fetch origin` |
| `git pull` | Descarga y fusiona automáticamente | `git pull origin main` |
| `git push` | Sube commits al servidor | `git push origin main` |
| `git push -u <remoto> <rama>` | Sube y establece upstream | Primera vez que subes rama |

**Escenario 6: Colaborar en GitHub**

```bash
# Paso 1: Crear repositorio en GitHub (desde navegador)
# Copiar URL: https://github.com/maria/mi-proyecto.git

# Paso 2: Conectar repositorio local con GitHub
git remote add origin https://github.com/maria/mi-proyecto.git

# Paso 3: Verificar conexión
git remote -v
# origin  https://github.com/maria/mi-proyecto.git (fetch)
# origin  https://github.com/maria/mi-proyecto.git (push)

# Paso 4: Subir código por primera vez
git push -u origin main

# Paso 5: Tu compañero clona el repositorio
git clone https://github.com/maria/mi-proyecto.git

# Paso 6: Tu compañero hace cambios
echo "<footer> 2025</footer>" >> index.html
git add index.html
git commit -m "Agregué footer"
git push

# Paso 7: Tú descargas los cambios
git pull

# Paso 8: Hacer tus propios cambios
echo "nav { background: blue; }" >> styles.css
git add styles.css
git commit -m "Cambié color del navbar"
git push
```

---

### Comandos Avanzados

| Comando | Descripción | Ejemplo |
|---------|-------------|----------|
| `git stash` | Guarda cambios temporales | `git stash save "mensaje"` |
| `git stash list` | Muestra lista de stash | Ver todos los stash |
| `git stash apply` | Aplica cambios de stash | `git stash apply stash@{0}` |
| `git stash drop` | Elimina stash | `git stash drop stash@{0}` |
| `git rebase` | Reorganiza commits | `git rebase -i HEAD~3` |
| `git cherry-pick` | Aplica commit específico | `git cherry-pick a3f5b21` |

**Escenario 7: Manejar cambios temporales**

```bash
# 1. Realizar cambios temporales
echo "body { background: red; }" >> styles.css

# 2. Guardar cambios en stash
git stash save "Cambios temporales"
# Saved working directory and index state On main: Cambios temporales

# 3. Verificar que cambios desaparecieron
git status
# On branch main, nothing to commit, working tree clean

# 4. Realizar otros cambios
echo "<h2>Encabezado</h2>" >> index.html
git add index.html
git commit -m "Agregué encabezado"

# 5. Aplicar cambios de stash
git stash apply stash@{0}
# Applied stash@{0}

# 6. Verificar que cambios volvieron
git status
# Changes not staged for commit:
#   modified:   styles.css
```

---

### Escenarios Avanzados

**Escenario 8: Reorganizar commits**

```bash
# 1. Realizar varios commits
echo "nav { background: blue; }" >> styles.css
git add styles.css
git commit -m "Cambié color del navbar"

echo "<footer> 2025</footer>" >> index.html
git add index.html
git commit -m "Agregué footer"

# 2. Reorganizar commits
git rebase -i HEAD~2
# pick a3f5b21 Cambié color del navbar
# pick f9e8d7c Agregué footer

# 3. Fusionar commits
git rebase -i HEAD~2
# pick a3f5b21 Cambié color del navbar y agregué footer

# 4. Verificar que commits se fusionaron
git log --oneline -2
# a3f5b21 Cambié color del navbar y agregué footer
# c3d4e56 Commit anterior
```

**Escenario 9: Deshacer cambios no guardados**

```bash
# Situación: Editaste un archivo pero quieres descartar los cambios

echo "Cambio experimental" >> index.html
git diff index.html
# +Cambio experimental

# Descartar el cambio
git restore index.html

git status
# nothing to commit, working tree clean
```

**Escenario 10: Corregir el último commit**

```bash
# Situación: Hiciste commit pero olvidaste agregar un archivo

git add index.html
git commit -m "Actualicé página de inicio"

# ¡Olvidaste el CSS!
echo "body { font-size: 16px; }" > styles.css
git add styles.css

# Modificar el commit anterior
git commit --amend --no-edit

# Resultado: El commit ahora incluye index.html Y styles.css
```

**Escenario 11: Revertir un commit publicado**

```bash
# Situación: Subiste un commit que rompe la aplicación

git log --oneline
# d4e5f67 (HEAD -> main, origin/main) Cambié configuración ⚠️ ROMPE
# c3d4e56 Agregué formulario

# Revertir el commit problemático
git revert d4e5f67
# Git abre editor para mensaje del commit

git log --oneline
# e5f6g78 (HEAD -> main) Revert "Cambié configuración"
# d4e5f67 (origin/main) Cambié configuración

git push
# ✅ El commit problemático sigue en historial pero sus cambios están deshechos
```

**Escenario 12: Limpiar commits con rebase interactivo**

```bash
# Situación: Hiciste muchos commits pequeños durante desarrollo

git log --oneline
# h8i9j0k Arreglé typo en comentario
# g7h8i9j Olvidé punto y coma
# f6g7h8i Agregué validación de email
# e5f6g7h Creé formulario de contacto
# d4e5f6g (main) Última versión estable

# Iniciar rebase interactivo
git rebase -i d4e5f6g

# Git abre editor con:
# pick e5f6g7h Creé formulario de contacto
# pick f6g7h8i Agregué validación de email
# pick g7h8i9j Olvidé punto y coma
# pick h8i9j0k Arreglé typo en comentario

# Cambiar a:
# pick e5f6g7h Creé formulario de contacto
# squash f6g7h8i Agregué validación de email
# squash g7h8i9j Olvidé punto y coma
# squash h8i9j0k Arreglé typo en comentario

# Guardar y cerrar - Git combina los 4 commits en uno solo

git log --oneline
# i9j0k1l Creé formulario de contacto con validación
# d4e5f6g (main) Última versión estable
```

**Escenario 13: Cherry-pick - Portar arreglo específico**

```bash
# Situación: Arreglaste un bug en desarrollo pero necesitas
# el arreglo en producción

# Estado:
# main (producción):     A---B---C
# desarrollo:            A---B---D---E---F (F es el arreglo)

# Identificar el commit del arreglo
git log desarrollo --oneline
# f7g8h91 (F) Arreglé bug de seguridad
# e6f7g80 (E) Nueva funcionalidad

# Cambiar a producción
git checkout main

# Aplicar solo el arreglo
git cherry-pick f7g8h91

# Resultado:
# main:                  A---B---C---F'
# desarrollo:            A---B---D---E---F

git push
```

**Escenario 14: Encontrar quién introdujo un bug**

```bash
# Ver quién editó cada línea
git blame app.js
# a3f5b21 (María  2025-11-10 10:30:00  15) function calcular() {
# 7d8e9f0 (Juan   2025-11-12 14:20:00  16)   return x + y;
# a3f5b21 (María  2025-11-10 10:30:00  17) }

# Ver el commit completo
git show 7d8e9f0
```

**Escenario 15: Recuperar commit "perdido" con reflog**

```bash
# Situación: Hiciste reset --hard por error

# Ver historial de movimientos
git reflog
# e5f6g78 HEAD@{0}: reset: moving to a1b2c34
# d4e5f67 HEAD@{1}: commit: Función importante (¡la perdiste!)
# c3d4e56 HEAD@{2}: commit: Otro cambio

# Recuperar el commit perdido
git checkout d4e5f67
# O crear una rama desde ahí
git checkout -b recuperado d4e5f67

# ¡Tu trabajo está de vuelta!
```

**Escenario 16: Encontrar cuándo apareció un bug (bisect)**

```bash
# Situación: La aplicación funcionaba hace 2 semanas, ahora falla

# Iniciar bisect
git bisect start
git bisect bad                    # El commit actual tiene el bug
git bisect good a1b2c34          # Este commit antiguo funcionaba

# Git hace checkout automático a un commit intermedio
# Salida: "Bisecting: 5 revisions left to test"

# Probar la aplicación
# Si funciona:
git bisect good
# Si falla:
git bisect bad

# Git sigue dividiendo el rango hasta encontrar el commit culpable

# Terminar bisect
git bisect reset
```

**Escenario 17: Resolver conflictos de merge**

```bash
# Situación: Dos personas editaron el mismo archivo

# Juan intenta subir después de María
git push
# ! [rejected] main -> main (fetch first)

# Juan descarga cambios
git pull
# Auto-merging index.html
# CONFLICT (content): Merge conflict in index.html

# Ver el conflicto
cat index.html
# <<<<<<< HEAD
# <header>Sitio de Juan</header>
# =======
# <header>Sitio de María</header>
# >>>>>>> a3f5b21

# Resolver manualmente (editar el archivo)
echo "<header>Sitio Colaborativo</header>" > index.html

# Marcar como resuelto
git add index.html
git commit -m "Resolví conflicto en header"
git push
```

**Escenario 18: Trabajar con múltiples remotos (Fork)**

```bash
# Situación: Quieres contribuir a un proyecto open source

# 1. Fork del proyecto en GitHub (desde navegador)
# 2. Clonar tu fork
git clone https://github.com/tu-usuario/proyecto.git
cd proyecto

# 3. Agregar el repositorio original como "upstream"
git remote add upstream https://github.com/original/proyecto.git

# 4. Ver remotos
git remote -v
# origin    https://github.com/tu-usuario/proyecto.git
# upstream  https://github.com/original/proyecto.git

# 5. Mantener tu fork actualizado
git fetch upstream
git checkout main
git merge upstream/main
git push origin main

# 6. Crear rama para tu contribución
git checkout -b mi-contribucion
echo "// Mi código" >> archivo.js
git add archivo.js
git commit -m "Agregué nueva funcionalidad"
git push origin mi-contribucion

# 7. Crear Pull Request desde GitHub
```

**Escenario 19: Usar .gitignore**

```bash
# Situación: Tienes archivos que no quieres versionar

# Crear archivo .gitignore
cat > .gitignore << EOF
# Dependencias
node_modules/
__pycache__/
*.pyc

# Archivos de configuración local
.env
config.local.js

# Archivos del sistema
.DS_Store
Thumbs.db

# Logs
*.log
logs/

# Archivos de build
dist/
build/
*.min.js
EOF

# Agregar .gitignore al repositorio
git add .gitignore
git commit -m "Agregué .gitignore"

# Si ya agregaste archivos que deberían ser ignorados
git rm --cached node_modules/ -r
git commit -m "Eliminé node_modules del repositorio"
```

**Escenario 20: Tags y versiones**

```bash
# Crear tag anotado (recomendado)
git tag -a v1.0.0 -m "Versión 1.0.0: Primera versión estable"

# Ver todos los tags
git tag

# Subir tags al remoto
git push origin --tags

# Crear tag en commit específico
git tag -a v0.9.0 a3f5b21 -m "Versión beta"

# Eliminar tag
git tag -d v0.9.0
git push origin --delete v0.9.0
```

---

### Mejores Prácticas

#### Mensajes de Commit

**Formato recomendado (Conventional Commits):**

```
<tipo>: <descripción corta>

<descripción detallada opcional>
```

**Tipos comunes:**
- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `docs`: Cambios en documentación
- `style`: Cambios de formato
- `refactor`: Refactorización de código
- `test`: Agregar o modificar tests
- `chore`: Tareas de mantenimiento

**Ejemplos:**

```bash
git commit -m "feat: Agregué sistema de autenticación"
git commit -m "fix: Corregí bug en validación de formulario"
git commit -m "docs: Actualicé README con instrucciones"
```

#### Estrategias de Branching

**Git Flow (proyectos grandes):**

```
main (producción)
  │
  ├── develop (desarrollo)
  │     ├── feature/nueva-funcionalidad
  │     ├── feature/otra-funcionalidad
  │     └── bugfix/arreglo-menor
  │
  ├── release/v1.0.0
  └── hotfix/bug-critico
```

**GitHub Flow (proyectos ágiles):**

```
main (siempre desplegable)
  ├── feature/nueva-funcionalidad
  ├── fix/bug-importante
  └── docs/actualizar-readme
```

#### Reglas de Oro

1. **Nunca hagas `git push --force` en ramas compartidas**: Puede sobrescribir el trabajo de otros
2. **Nunca hagas rebase de commits ya publicados**: Reescribe la historia y causa conflictos
3. **Haz commits pequeños y atómicos**: Un commit = un cambio lógico
4. **Escribe mensajes de commit descriptivos**: Tu yo del futuro te lo agradecerá
5. **Revisa antes de hacer commit**: Usa `git diff` y `git status`
6. **Haz pull antes de push**: Evita conflictos innecesarios
7. **Usa ramas para todo**: Nunca trabajes directamente en main
8. **Elimina ramas fusionadas**: Mantén el repositorio limpio

#### Comandos Útiles del Día a Día

```bash
# Ver estado de forma compacta
git status -s

# Ver historial de forma bonita
git log --oneline --graph --decorate --all

# Crear alias para comandos largos
git config --global alias.lg "log --oneline --graph --decorate --all"
# Ahora puedes usar: git lg

# Buscar en el historial
git log --grep="bug"

# Ver archivos modificados en un commit
git show --name-only a3f5b21

# Comparar dos ramas
git diff main..feature/nueva-funcionalidad

# Ver ramas fusionadas
git branch --merged

# Ver ramas no fusionadas
git branch --no-merged
```

---

## Tabla Resumen de Comandos

### Comandos Básicos

| Comando | Descripción |
|---------|-------------|
| `git init` | Inicializar repositorio |
| `git clone <url>` | Clonar repositorio |
| `git status` | Ver estado de archivos |
| `git add <archivo>` | Preparar archivos |
| `git commit -m "mensaje"` | Guardar cambios |
| `git push` | Subir cambios |
| `git pull` | Descargar cambios |
| `git branch` | Listar ramas |
| `git checkout <rama>` | Cambiar de rama |
| `git merge <rama>` | Fusionar ramas |

### Comandos Avanzados

| Comando | Descripción |
|---------|-------------|
| `git stash` | Guarda cambios temporales |
| `git rebase -i` | Rebase interactivo |
| `git cherry-pick` | Copiar commit específico |
| `git revert` | Deshacer commit (seguro) |
| `git reset --hard` | Deshacer commit (peligroso) |
| `git reflog` | Ver historial de HEAD |
| `git bisect` | Buscar bug binariamente |
| `git blame` | Ver quién modificó líneas |
| `git tag` | Crear etiquetas |
| `git remote add` | Agregar remoto |

---

## Recursos Adicionales

### Documentación Oficial
- [Git Documentation](https://git-scm.com/doc)
- [Pro Git Book](https://git-scm.com/book/es/v2)

### Herramientas Visuales
- **GitKraken**: Cliente visual multiplataforma
- **SourceTree**: Cliente visual gratuito
- **GitHub Desktop**: Cliente oficial de GitHub
- **Git Graph** (VS Code): Extensión para visualizar historial

### Práctica Interactiva
- [Learn Git Branching](https://learngitbranching.js.org/)
- [Git Immersion](https://gitimmersion.com/)

---

**¡Felicidades! Ahora tienes una guía completa de Git con 20 escenarios prácticos desde básicos hasta avanzados.**

## Planificación y Tiempos de Ejecución

### 📊 Estimación de Tiempo por Secciones

#### **Nivel Básico (Escenarios 1-6)**
**Tiempo estimado: 2-3 horas**

- **Configuración inicial**: 15 minutos
- **Crear y clonar repositorios**: 20 minutos
- **Ciclo básico de trabajo**: 30 minutos
- **Ver historial y diferencias**: 25 minutos
- **Trabajar con ramas**: 40 minutos
- **Repositorios remotos**: 50 minutos

#### **Nivel Avanzado (Escenarios 7-20)**
**Tiempo estimado: 4-6 horas**

- **Stash y cambios temporales**: 30 minutos
- **Deshacer cambios (restore, amend, revert)**: 45 minutos
- **Rebase interactivo**: 45 minutos
- **Cherry-pick**: 30 minutos
- **Investigación (blame, reflog, bisect)**: 1 hora
- **Resolver conflictos**: 45 minutos
- **Múltiples remotos (Fork)**: 40 minutos
- **.gitignore**: 20 minutos
- **Tags y versiones**: 25 minutos

#### **Mejores Prácticas y Comandos Útiles**
**Tiempo estimado: 1-1.5 horas**

- **Mensajes de commit**: 20 minutos
- **Estrategias de branching**: 30 minutos
- **Comandos útiles y alias**: 20 minutos

---

### ⏱️ Formatos de Taller Recomendados

#### **Opción 1: Taller Básico (Medio Día)**
**Duración**: 3-4 horas

```
09:00 - 09:30  Introducción y conceptos fundamentales
09:30 - 10:15  Escenarios 1-2: Configuración y repositorios
10:15 - 10:30  Break
10:30 - 11:15  Escenarios 3-4: Ciclo de trabajo e historial
11:15 - 12:00  Escenarios 5-6: Ramas y remotos
12:00 - 12:30  Práctica guiada y Q&A
```

**Incluye**: Comandos básicos esenciales para trabajo diario

---

#### **Opción 2: Taller Completo (1 Día)**
**Duración**: 6-8 horas

```
SESIÓN MAÑANA (3-4 horas)
09:00 - 09:30  Introducción y conceptos
09:30 - 11:00  Escenarios 1-3: Fundamentos básicos
11:00 - 11:15  Break
11:15 - 12:30  Escenarios 4-6: Ramas y remotos

SESIÓN TARDE (3-4 horas)
14:00 - 15:30  Escenarios 7-10: Comandos avanzados
15:30 - 15:45  Break
15:45 - 17:00  Escenarios 11-15: Depuración
17:00 - 17:30  Mejores prácticas y Q&A
```

**Incluye**: Comandos básicos + avanzados esenciales

---

#### **Opción 3: Curso Completo (2 Días)**
**Duración**: 12 horas

```
DÍA 1 (6 horas)
MAÑANA
09:00 - 09:30  Introducción y conceptos fundamentales
09:30 - 11:00  Escenarios 1-4: Fundamentos
11:00 - 11:15  Break
11:15 - 12:30  Escenarios 5-6: Ramas y remotos

TARDE
14:00 - 15:30  Escenarios 7-9: Stash y deshacer cambios
15:30 - 15:45  Break
15:45 - 17:00  Escenarios 10-12: Rebase y reorganización
17:00 - 17:30  Ejercicios prácticos Día 1

DÍA 2 (6 horas)
MAÑANA
09:00 - 10:30  Escenarios 13-16: Cherry-pick y depuración
10:30 - 10:45  Break
10:45 - 12:30  Escenarios 17-20: Conflictos, fork, tags

TARDE
14:00 - 15:00  Mejores prácticas y estrategias
15:00 - 15:15  Break
15:15 - 16:30  Proyecto práctico integrador
16:30 - 17:00  Evaluación y cierre
```

**Incluye**: Todos los escenarios + proyecto práctico

---

#### **Opción 4: Curso Distribuido (4 Sesiones)**
**Duración**: 12-16 horas (3-4 horas por sesión)

```
SESIÓN 1: Fundamentos (3 horas)
├── Conceptos y configuración (30 min)
├── Escenarios 1-4 (2 horas)
└── Ejercicios prácticos (30 min)

SESIÓN 2: Colaboración (3 horas)
├── Escenarios 5-6: Ramas y remotos (1.5 horas)
├── Escenarios 7-8: Stash y reorganizar (1 hora)
└── Ejercicios en grupo (30 min)

SESIÓN 3: Comandos Avanzados (4 horas)
├── Escenarios 9-13: Deshacer, rebase, cherry-pick (2 horas)
├── Escenarios 14-16: Depuración (1.5 horas)
└── Práctica con problemas reales (30 min)

SESIÓN 4: Escenarios Profesionales (3 horas)
├── Escenarios 17-20: Conflictos, fork, tags (1.5 horas)
├── Mejores prácticas (30 min)
├── Proyecto final (45 min)
└── Evaluación (15 min)
```

**Incluye**: Todo el contenido + tiempo para práctica profunda

---

### 🎯 Factores que Afectan el Tiempo

#### **Aumentan el tiempo (+):**
- ❌ Participantes sin experiencia en línea de comandos: **+30%**
- ❌ Problemas técnicos de configuración: **+1 hora**
- ❌ Grupos grandes (>15 personas): **+20%**
- ❌ Muchas preguntas y discusiones: **+30%**
- ❌ Ejercicios adicionales personalizados: **+1-2 horas**

#### **Reducen el tiempo (-):**
- ✅ Participantes con experiencia básica en terminal: **-20%**
- ✅ Entorno pre-configurado (Git ya instalado): **-30 min**
- ✅ Material de apoyo visual (slides, diagramas): **-15%**
- ✅ Ejercicios preparados con anticipación: **-20%**
- ✅ Asistente o instructor de apoyo: **-25%**

---

### 📚 Recomendación para Autoaprendizaje

```
SEMANA 1: Fundamentos (2-3 horas)
├── Leer conceptos fundamentales
├── Practicar escenarios 1-6
└── Crear repositorio personal de prueba

SEMANA 2: Comandos Avanzados Parte 1 (3-4 horas)
├── Estudiar comandos avanzados
├── Practicar escenarios 7-13
└── Experimentar con rebase y cherry-pick

SEMANA 3: Comandos Avanzados Parte 2 (3-4 horas)
├── Practicar escenarios 14-20
├── Simular conflictos y resolverlos
└── Trabajar con fork de proyecto real

SEMANA 4: Consolidación (2-3 horas)
├── Repasar mejores prácticas
├── Aplicar en proyecto personal
├── Crear cheat sheet personalizado
└── Evaluar conocimientos

TOTAL: 10-14 horas distribuidas en 4 semanas
```

---

### 💡 Consejos para Maximizar el Aprendizaje

1. **Práctica Hands-on**: Ejecutar cada comando mientras lees
2. **Crear Repositorio de Prueba**: Experimentar sin miedo a romper algo
3. **Tomar Notas**: Documentar comandos que más usas
4. **Cometer Errores**: Aprender a recuperarse de errores es crucial
5. **Proyectos Reales**: Aplicar lo aprendido en proyectos personales
6. **Revisión Periódica**: Repasar conceptos cada 2-3 semanas

---

### 📋 Checklist de Preparación para Talleres

#### **Antes del Taller:**
- [ ] Git instalado en todas las máquinas
- [ ] Cuentas de GitHub/GitLab creadas
- [ ] Editor de texto configurado
- [ ] Repositorio de práctica preparado
- [ ] Material de apoyo impreso/digital
- [ ] Proyector y conexión a internet verificados

#### **Durante el Taller:**
- [ ] Tiempo para preguntas después de cada sección
- [ ] Ejercicios prácticos entre escenarios
- [ ] Breaks cada 90 minutos
- [ ] Asistencia para problemas técnicos
- [ ] Documentación de dudas frecuentes

#### **Después del Taller:**
- [ ] Material complementario compartido
- [ ] Ejercicios adicionales opcionales
- [ ] Canal de comunicación para dudas
- [ ] Evaluación de satisfacción
- [ ] Certificado de participación (opcional)

---

**¡Felicidades! Ahora tienes una guía completa de Git con 20 escenarios prácticos desde básicos hasta avanzados, incluyendo planificación detallada para diferentes formatos de enseñanza.**