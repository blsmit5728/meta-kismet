# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

SUMMARY = "Kismet is an 802.11 network sniffer and network dissector."
HOMEPAGE = "www.kismetwireless.net"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
#   fmt/LICENSE.rst
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://kismet-2021-08-R1/LICENSE;md5=21d24e9d61cfe0fd83aebabd3788785c"

SRC_URI = "https://www.kismetwireless.net/code/kismet-2021-08-R1.tar.xz"

# Modify these as desired
PV = "2021-08-R1"
SRC_URI[md5sum] = "9fc3157767fd3526d5354e18d11afd37"

S = "${WORKDIR}"

# NOTE: the following prog dependencies are unknown, ignoring: protoc protoc-c
# NOTE: unable to map the following pkg-config dependencies: libnm libprotobuf-c libwebsockets libnl-1 libnl-2.0 libbladeRF
#       (this is based on recipes that have previously been built and packaged)
# NOTE: the following library dependencies are unknown, ignoring: btbb sensors bfd websockets ubertooth hackrf protobuf-c bladeRF
#       (this is based on recipes that have previously been built and packaged)
DEPENDS = "protobuf-c-native protobuf-c protobuf protobuf-native libwebsockets libusb1 zlib libpcre sqlite3 libpcap libnl elfutils libcap fftw"

# NOTE: if this software is not capable of being built in a separate build directory
# from the source, you should replace autotools with autotools-brokensep in the
# inherit line
inherit autotools-brokensep

# Specify any options you want to pass to the configure script using EXTRA_OECONF:
EXTRA_OECONF = ""

do_configure() {
	pwd
        cd kismet-2021-08-R1
	pwd
	#oe_runconf
	./configure
}

do_compile() {
	cd kismet-2021-08-R1
	oe_runmake
}

fakeroot do_install() {
     oe_runmake "DESTDIR=${D}" suidinstall
}

do_install_append() {
	if test -e ${WORKDIR}/kismet.conf; then
		install -m 644 ${WORKDIR}/kismet.conf ${D}${sysconfdir}/
	fi
}


