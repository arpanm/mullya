import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './address.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AddressDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="mulyaaApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="streetAddress">
              <Translate contentKey="mulyaaApp.address.streetAddress">Street Address</Translate>
            </span>
          </dt>
          <dd>{addressEntity.streetAddress}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="mulyaaApp.address.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{addressEntity.postalCode}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="mulyaaApp.address.city">City</Translate>
            </span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="stateProvince">
              <Translate contentKey="mulyaaApp.address.stateProvince">State Province</Translate>
            </span>
          </dt>
          <dd>{addressEntity.stateProvince}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="mulyaaApp.address.country">Country</Translate>
            </span>
          </dt>
          <dd>{addressEntity.country}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mulyaaApp.address.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.createdBy}</dd>
          <dt>
            <span id="lat">
              <Translate contentKey="mulyaaApp.address.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{addressEntity.lat}</dd>
          <dt>
            <span id="lon">
              <Translate contentKey="mulyaaApp.address.lon">Lon</Translate>
            </span>
          </dt>
          <dd>{addressEntity.lon}</dd>
          <dt>
            <span id="mapLocation">
              <Translate contentKey="mulyaaApp.address.mapLocation">Map Location</Translate>
            </span>
          </dt>
          <dd>{addressEntity.mapLocation}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mulyaaApp.address.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {addressEntity.createdOn ? <TextFormat value={addressEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mulyaaApp.address.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {addressEntity.updatedOn ? <TextFormat value={addressEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mulyaaApp.address.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.updatedBy}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.address.hub">Hub</Translate>
          </dt>
          <dd>{addressEntity.hub ? addressEntity.hub.id : ''}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.address.actor">Actor</Translate>
          </dt>
          <dd>{addressEntity.actor ? addressEntity.actor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
