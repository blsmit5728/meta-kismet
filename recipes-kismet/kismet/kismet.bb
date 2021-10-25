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
LIC_FILES_CHKSUM = "file://git/LICENSE;md5=21d24e9d61cfe0fd83aebabd3788785c"

#SRC_URI = "https://www.kismetwireless.net/code/kismet-2021-08-R1.tar.xz"
SRC_URI = "git://www.kismetwireless.net/git/kismet.git;protocol=https;nobranch=1 \
          file://no-group-make.patch"

# Modify these as desired
PV = "Kismet-2021-08-R1"
PN = "kismet"
SRCREV = "d9555f37792f61a4d9f39bce6b78643a8a4beda7"

S = "${WORKDIR}"

FILES_${PN} += " \
	${libdir}/python3.5/site-packages/Kismet* \
	"

# NOTE: the following prog dependencies are unknown, ignoring: protoc protoc-c
# NOTE: unable to map the following pkg-config dependencies: libnm libprotobuf-c libwebsockets libnl-1 libnl-2.0 libbladeRF
#       (this is based on recipes that have previously been built and packaged)
# NOTE: the following library dependencies are unknown, ignoring: btbb sensors bfd websockets ubertooth hackrf protobuf-c bladeRF
#       (this is based on recipes that have previously been built and packaged)
DEPENDS = "python3 protobuf-c-native protobuf-c protobuf protobuf-native libwebsockets libusb1 zlib libpcre sqlite3 libpcap libnl elfutils libcap fftw"

# NOTE: if this software is not capable of being built in a separate build directory
# from the source, you should replace autotools with autotools-brokensep in the
# inherit line
inherit autotools-brokensep
inherit useradd

# You must set USERADD_PACKAGES when you inherit useradd. This
# lists which output packages will include the user/group
# creation code.
USERADD_PACKAGES = "${PN}"

# Specify any options you want to pass to the configure script using EXTRA_OECONF:
EXTRA_OECONF = ""

GROUPADD_PARAM:${PN} = "-r -f kismet"

ONFIGUREOPTS = " --build=${BUILD_SYS} \
		  --host=${HOST_SYS} \
		  --target=${TARGET_SYS} \
		  --prefix=${prefix} \
		  --exec_prefix=${exec_prefix} \
		  --bindir=${bindir} \
		  --sbindir=${sbindir} \
		  --libexecdir=${libexecdir} \
		  --datadir=${datadir} \
		  --sysconfdir=${sysconfdir} \
		  --sharedstatedir=${sharedstatedir} \
		  --localstatedir=${localstatedir} \
		  --libdir=${libdir} \
		  --includedir=${includedir} \
		  --oldincludedir=${oldincludedir} \
		  --infodir=${infodir} \
		  --mandir=${mandir} \
		  --disable-silent-rules \
		  ${CONFIGUREOPT_DEPTRACK} \
		  ${@append_libtool_sysroot(d)}"

do_configure() {
	cd git
        bbdebug 1 "${ONFIGUREOPTS}"
	./configure ${ONFIGUREOPTS}
}

do_compile() {
	cd git
	make -j
} 

fakeroot do_install() {
	cd git
	oe_runmake "DESTDIR=${D}" suidinstall
}

#fakeroot do_install() {
#     oe_runmake "DESTDIR=${D}" suidinstall
#}

#do_install_append() {
#	if test -e ${WORKDIR}/kismet.conf; then
#		install -m 644 ${WORKDIR}/kismet.conf ${D}${sysconfdir}/
#	fi
#}


