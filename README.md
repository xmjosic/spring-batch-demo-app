# spring-batch-demo-app

- Spring batch
- 2 jobs: insert from CSV to the DB, update second table if CSV table has been changed
- Rest controller to start initial job, start second job

To start initial job:
```bash
curl -X POST "http://localhost:8181/api/v1/batch/start/zero
```

To start second job
```bash
curl -X POST "http://localhost:8181/api/v1/batch/start
```
