The startup order is config,discovery, services, and finally gateway.   If its started out of order things tend to not work.

All services except config (which looks for the discovery service post startup) will startup without issue.  If you do see an error... restart the offending service IN ORDER

Just to play around all 403s and 401s are redirect to /login.  In the real world this would probably be more sophisticated but the point was to show that streams can be redirected.