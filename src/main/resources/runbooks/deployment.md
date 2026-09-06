# Deployment runbook

- Identify the release version, deployment time, affected service, and change owner.
- Compare the current release, configuration, and environment variables with the last known-good version.
- Review CI/CD output, deployment events, startup logs, and health-check results.
- Verify compatibility of recent database migrations, feature flags, secrets, and dependent-service configuration.
- If the release is the confirmed cause and impact is high, follow the approved rollback or mitigation procedure.