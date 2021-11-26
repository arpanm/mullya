import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './otp.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OTPDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const oTPEntity = useAppSelector(state => state.oTP.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="oTPDetailsHeading">
          <Translate contentKey="mullyaApp.oTP.detail.title">OTP</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.id}</dd>
          <dt>
            <span id="otp">
              <Translate contentKey="mullyaApp.oTP.otp">Otp</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.otp}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="mullyaApp.oTP.email">Email</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="mullyaApp.oTP.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.phone}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="mullyaApp.oTP.type">Type</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.type}</dd>
          <dt>
            <span id="expiryTime">
              <Translate contentKey="mullyaApp.oTP.expiryTime">Expiry Time</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.expiryTime ? <TextFormat value={oTPEntity.expiryTime} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="mullyaApp.oTP.status">Status</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.status}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mullyaApp.oTP.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="mullyaApp.oTP.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.createdAt ? <TextFormat value={oTPEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mullyaApp.oTP.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="mullyaApp.oTP.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.updatedAt ? <TextFormat value={oTPEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="mullyaApp.oTP.actor">Actor</Translate>
          </dt>
          <dd>{oTPEntity.actor ? oTPEntity.actor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/otp" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/otp/${oTPEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OTPDetail;
