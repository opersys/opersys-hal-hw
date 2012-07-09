/*
 * Copyright (C) 2012 Opersys inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <errno.h>

#define  LOG_TAG  "opersyshw_qemu"
#include <cutils/log.h>
#include <cutils/sockets.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <hardware/opersyshw.h>

#define   OPERSYSHW_DEBUG   1

#if OPERSYSHW_DEBUG
#  define D(...)   LOGD(__VA_ARGS__)
#else
#  define D(...)   ((void)0)
#endif

int fd = 0;

int opersyshw__read(char* buffer, int length)
{
    int retval;

    D("OPERSYS HW - read()for %d bytes called", length);

    retval = read(fd, buffer, length);    

    return retval;
}

int opersyshw__write(char* buffer, int length)
{
    int retval;

    D("OPERSYS HW - write()for %d bytes called", length);

    retval = write(fd, buffer, length);

    return retval;
}

int opersyshw__test(int value)
{
    return value;
}

static int open_opersyshw(const struct hw_module_t* module, char const* name,
        struct hw_device_t** device)
{
    struct opersyshw_device_t *dev = malloc(sizeof(struct opersyshw_device_t));
    memset(dev, 0, sizeof(*dev));

    dev->common.tag = HARDWARE_DEVICE_TAG;
    dev->common.version = 0;
    dev->common.module = (struct hw_module_t*)module;
    dev->read = opersyshw__read;
    dev->write = opersyshw__write;
    dev->test = opersyshw__test;

    *device = (struct hw_device_t*) dev;

    fd = open("/dev/circchar", O_RDWR);

    D("OPERSYS HW has been initialized");

    return 0;
}

static struct hw_module_methods_t opersyshw_module_methods = {
    .open = open_opersyshw
};

const struct hw_module_t HAL_MODULE_INFO_SYM = {
    .tag = HARDWARE_MODULE_TAG,
    .version_major = 1,
    .version_minor = 0,
    .id = OPERSYSHW_HARDWARE_MODULE_ID,
    .name = "Opersys HW Module",
    .author = "Opersys inc.",
    .methods = &opersyshw_module_methods,
};
