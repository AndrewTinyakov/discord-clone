rootProject.name = "discord-clone"
include(
    "core:config-server",
    "core:gateway",
    "core:service-registry",
    "services:auth-service",
    "services:user-service",
    "services:ws-service",
    "common:auth",
    "common:events"
)