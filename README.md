# CyberTrace

AI-Powered Email Threat Detection, GeoLocation & Forensic Intelligence Platform — SIH26106.

## Architecture
React frontend → Java/Spring Boot API → PostgreSQL + Python AI service → IOC/Geo enrichment → forensic case → SHA-256 evidence hash → blockchain proof → report.

## Quick start
1. Set `DB_PASSWORD` in your shell/environment.
2. Start PostgreSQL and create database `cybertrace`.
3. Backend: `cd backend && mvn spring-boot:run`.
4. AI service: `cd ai-service && python -m venv .venv && .venv/Scripts/pip install -r requirements.txt` (Windows) or `.venv/bin/pip` (Linux/macOS), then `uvicorn app:app --reload --port 8000`.
5. Frontend: `cd frontend && npm install && npm run dev`.

Docker Compose is also provided for PostgreSQL, backend, AI service and frontend.

## Security notes
- Never commit real passwords/API keys.
- Email evidence is treated as untrusted input.
- Attachments are not executed by the application.
- GeoLocation is approximate and must not be treated as exact person attribution.
- Blockchain stores hashes/metadata, not raw email content.
