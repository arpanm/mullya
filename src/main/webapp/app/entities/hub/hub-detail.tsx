import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './hub.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const HubDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const hubEntity = useAppSelector(state => state.hub.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hubDetailsHeading">
          <Translate contentKey="mulyaaApp.hub.detail.title">Hub</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hubEntity.id}</dd>
          <dt>
            <span id="tag">
              <Translate contentKey="mulyaaApp.hub.tag">Tag</Translate>
            </span>
          </dt>
          <dd>{hubEntity.tag}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="mulyaaApp.hub.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{hubEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mulyaaApp.hub.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{hubEntity.createdOn ? <TextFormat value={hubEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mulyaaApp.hub.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{hubEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mulyaaApp.hub.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{hubEntity.updatedOn ? <TextFormat value={hubEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mulyaaApp.hub.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{hubEntity.updatedBy}</dd>
        </dl>
        <Button tag={Link} to="/hub" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hub/${hubEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HubDetail;
