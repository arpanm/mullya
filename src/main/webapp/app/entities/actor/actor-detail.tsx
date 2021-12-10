import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './actor.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ActorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const actorEntity = useAppSelector(state => state.actor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="actorDetailsHeading">
          <Translate contentKey="mulyaaApp.actor.detail.title">Actor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{actorEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="mulyaaApp.actor.email">Email</Translate>
            </span>
          </dt>
          <dd>{actorEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="mulyaaApp.actor.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{actorEntity.phone}</dd>
          <dt>
            <span id="isEmailVerified">
              <Translate contentKey="mulyaaApp.actor.isEmailVerified">Is Email Verified</Translate>
            </span>
          </dt>
          <dd>{actorEntity.isEmailVerified ? 'true' : 'false'}</dd>
          <dt>
            <span id="isPhoneVerified">
              <Translate contentKey="mulyaaApp.actor.isPhoneVerified">Is Phone Verified</Translate>
            </span>
          </dt>
          <dd>{actorEntity.isPhoneVerified ? 'true' : 'false'}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="mulyaaApp.actor.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{actorEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="mulyaaApp.actor.password">Password</Translate>
            </span>
          </dt>
          <dd>{actorEntity.password}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="mulyaaApp.actor.type">Type</Translate>
            </span>
          </dt>
          <dd>{actorEntity.type}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mulyaaApp.actor.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{actorEntity.createdOn ? <TextFormat value={actorEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mulyaaApp.actor.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{actorEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mulyaaApp.actor.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{actorEntity.updatedOn ? <TextFormat value={actorEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mulyaaApp.actor.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{actorEntity.updatedBy}</dd>
        </dl>
        <Button tag={Link} to="/actor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/actor/${actorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActorDetail;
