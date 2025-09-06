rootProject.name = "discord-clone"
include(
    "core:config-server",
    "core:gateway",
    "core:service-registry",
    "services:auth-service",
    "services:user-service",
    "services:chat-service",
    "services:message-service",
    "services:aggregators:messenger-aggregator",
    "common:auth",
    "common:events",
    "common:pagination",
    "common:clients"
)
include("common:dto")