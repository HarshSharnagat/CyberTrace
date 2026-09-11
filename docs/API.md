# API

- `GET /` — backend health text.
- `GET /api/users/count` — database smoke test.
- `POST /api/analyze` — multipart form field `file`, accepts an email evidence file such as `.eml`.
- `GET /api/cases` — list forensic cases.
- `GET /api/cases/{id}` — fetch one case.
- AI service: `POST http://localhost:8000/predict` with `{subject, body, sender}`.
