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
          <Translate contentKey="mulyaaApp.oTP.detail.title">OTP</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.id}</dd>
          <dt>
            <span id="otpVal">
              <Translate contentKey="mulyaaApp.oTP.otpVal">Otp Val</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.otpVal}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="mulyaaApp.oTP.email">Email</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="mulyaaApp.oTP.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.phone}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="mulyaaApp.oTP.type">Type</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.type}</dd>
          <dt>
            <span id="expiryTime">
              <Translate contentKey="mulyaaApp.oTP.expiryTime">Expiry Time</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.expiryTime ? <TextFormat value={oTPEntity.expiryTime} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="mulyaaApp.oTP.status">Status</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.status}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mulyaaApp.oTP.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.createdOn ? <TextFormat value={oTPEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mulyaaApp.oTP.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mulyaaApp.oTP.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.updatedOn ? <TextFormat value={oTPEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mulyaaApp.oTP.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{oTPEntity.updatedBy}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.oTP.actor">Actor</Translate>
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
