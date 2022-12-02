SUMMARY = "A tester's image that fully supports the target device \
hardware."

IMAGE_FEATURES += "splash nfs-server nfs-client ssh-server-openssh tools-sdk"

LICENSE = "MIT"

inherit core-image

CORE_IMAGE_EXTRA_INSTALL = "packagegroup-core-full-cmdline"

IMAGE_INSTALL += "acpica apt"
