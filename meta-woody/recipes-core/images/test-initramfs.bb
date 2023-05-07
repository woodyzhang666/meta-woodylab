# Simple initramfs image. Mostly used for live images.
DESCRIPTION = "Small image capable of booting a device. The kernel includes \
the Minimal RAM-based Initial Root Filesystem (initramfs), which finds the \
first 'init' program more efficiently."

INITRAMFS_SCRIPTS ?= "\
                      initramfs-framework-base \
                      initramfs-module-setup-live \
                      initramfs-module-udev \
                      initramfs-module-install \
                      initramfs-module-install-efi \
                      initramfs-module-nfsrootfs \
                      initramfs-module-debug \
                      initramfs-module-e2fs \
                     "

# As we don't use busybox, enlarge initramfs size limit to 1GB
#INITRAMFS_MAXSIZE = "1048576"

PACKAGE_INSTALL = "kernel-modules ${INITRAMFS_SCRIPTS} busybox udev base-passwd ${ROOTFS_BOOTSTRAP_INSTALL}"

# Do not pollute the initrd image with rootfs features
IMAGE_FEATURES = "debug-tweaks"

export IMAGE_BASENAME = "${MLPREFIX}test-initramfs"
IMAGE_NAME_SUFFIX ?= ""
IMAGE_LINGUAS = ""

LICENSE = "MIT"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES} ext4"
inherit core-image

IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

# Use the same restriction as initramfs-module-install
COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*|loongarch64.*)-(linux.*|freebsd.*)'
