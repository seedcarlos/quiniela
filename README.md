# Quiniela Mundial 2026

Aplicación Spring Boot + PostgreSQL para administrar una quiniela del mundial 2026 con roles de administración y participantes.

## Funcionalidades principales

- Catálogos de equipos y partidos por fase.
- Registro de participantes y captura de pronósticos.
- Cálculo de puntajes:
  - 2 puntos por acertar el resultado (local/visitante/empate).
  - 1 punto por acertar goles del local.
  - 1 punto por acertar goles del visitante.
- Selección de campeón: +5 puntos si el equipo elegido gana la copa.

## Ejecución con Docker

```bash
docker compose up --build
```

La API queda disponible en `http://localhost:8080`.

## Variables de entorno

- `DB_HOST` (default `localhost`)
- `DB_PORT` (default `5432`)
- `DB_NAME` (default `quiniela`)
- `DB_USER` (default `quiniela`)
- `DB_PASSWORD` (default `quiniela`)
- `PORT` (default `8080`)

## Endpoints principales

### Administración

- `POST /admin/teams`

```json
{ "name": "México", "groupName": "A" }
```

- `POST /admin/matches`

```json
{ "homeTeamId": 1, "awayTeamId": 2, "phase": "Fase de grupos", "kickoffAt": "2026-06-10T16:00:00" }
```

- `POST /admin/matches/{matchId}/result`

```json
{ "homeGoals": 2, "awayGoals": 1 }
```

- `POST /admin/champion`

```json
{ "teamId": 1 }
```

### Participantes

- `POST /participants/register`

```json
{ "name": "Luis", "email": "luis@example.com" }
```

- `POST /participants/{userId}/predictions`

```json
{ "matchId": 10, "homeGoals": 2, "awayGoals": 1 }
```

- `POST /participants/{userId}/champion`

```json
{ "teamId": 1 }
```

- `GET /participants/leaderboard`

## Puntuación final

El total de puntos se calcula con la suma de los pronósticos evaluados más el bono de campeón (5 puntos si el equipo elegido gana la copa).
