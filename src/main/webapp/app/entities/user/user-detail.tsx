import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './user.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const UserDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const userEntity = useAppSelector(state => state.user.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userDetailsHeading">
          <Translate contentKey="mulyaaApp.user.detail.title">User</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="mulyaaApp.user.email">Email</Translate>
            </span>
          </dt>
          <dd>{userEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="mulyaaApp.user.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{userEntity.phone}</dd>
          <dt>
            <span id="isEmailVerified">
              <Translate contentKey="mulyaaApp.user.isEmailVerified">Is Email Verified</Translate>
            </span>
          </dt>
          <dd>{userEntity.isEmailVerified ? 'true' : 'false'}</dd>
          <dt>
            <span id="isPhoneVerified">
              <Translate contentKey="mulyaaApp.user.isPhoneVerified">Is Phone Verified</Translate>
            </span>
          </dt>
          <dd>{userEntity.isPhoneVerified ? 'true' : 'false'}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="mulyaaApp.user.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{userEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="mulyaaApp.user.password">Password</Translate>
            </span>
          </dt>
          <dd>{userEntity.password}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="mulyaaApp.user.type">Type</Translate>
            </span>
          </dt>
          <dd>{userEntity.type}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mulyaaApp.user.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{userEntity.createdOn ? <TextFormat value={userEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mulyaaApp.user.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{userEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mulyaaApp.user.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{userEntity.updatedOn ? <TextFormat value={userEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="lastModifiedBy">
              <Translate contentKey="mulyaaApp.user.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{userEntity.updatedBy}</dd>
        </dl>
        <Button tag={Link} to="/user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user/${userEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserDetail;
