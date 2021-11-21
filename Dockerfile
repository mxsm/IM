FROM openjdk:11

LABEL author="mxsm" \
      author.email="ljbmxsm@gmail.com"

# set env
ENV IM_VERSION="im-0.1-SNAPSHOT" \
    BASEDIR="/home/im"

WORKDIR $BASEDIR

COPY distribution/target/${IM_VERSION}.tar.gz $BASEDIR
RUN tar -zxvf ${IM_VERSION}.tar.gz -C $BASEDIR \
    && rm -rf ${IM_VERSION}.tar.gz \
    && mv ${BASEDIR}/${IM_VERSION}/*  ${BASEDIR} \
    && rm -rf ${IM_VERSION} \
    && ls -al

EXPOSE 9998

CMD bin/imregister.sh -c conf/register.conf