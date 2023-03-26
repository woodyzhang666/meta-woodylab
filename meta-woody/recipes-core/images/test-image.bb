SUMMARY = "A tester's image that fully supports the target device \
hardware."

IMAGE_FEATURES += "splash nfs-server nfs-client ssh-server-openssh \
    tools-sdk debug-tweaks package-management bash-completion-pkgs \
    dev-pkgs dbg-pkgs lic-pkgs"

LICENSE = "MIT"

LICENSE_CREATE_PACKAGE = "1"

#IMAGE_INSTALL += "packagegroup-meta-oe"

inherit core-image

CORE_IMAGE_EXTRA_INSTALL = "packagegroup-core-full-cmdline"
