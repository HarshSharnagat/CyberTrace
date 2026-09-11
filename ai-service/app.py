from fastapi import FastAPI
from pydantic import BaseModel
import re

app = FastAPI(title="CyberTrace AI Service", version="1.0.0")

class EmailInput(BaseModel):
    subject: str = ""
    body: str = ""
    sender: str = ""

@app.get("/")
def root(): return {"service":"CyberTrace AI","status":"running"}

@app.post("/predict")
def predict(data: EmailInput):
    text=f"{data.subject} {data.body}".lower()
    signals=[]; score=0
    for word in ["urgent","verify","suspended","password","account","click"]:
        if re.search(rf"\b{re.escape(word)}\b", text): score += 10; signals.append(word)
    if re.search(r"https?://", text): score += 20; signals.append("url")
    score=min(score,100)
    return {"score":score,"label":"phishing" if score>=40 else "benign_or_uncertain","signals":signals}
